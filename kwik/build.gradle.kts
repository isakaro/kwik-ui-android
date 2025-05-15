import com.vanniktech.maven.publish.SonatypeHost
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Base64
import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("maven-publish")
    id("signing")
    id("com.vanniktech.maven.publish") version "0.31.0"
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.documentation.plugin)
}

object Meta {
    const val Descripton = "A Jetpack Compose library for building UI components."
    const val License = "Apache-2.0"
    const val GithubRepository = "isakaro/kwik-ui-android"
}

val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretsProperties = Properties()
secretsProperties.load(FileInputStream(secretsPropertiesFile))

val versionProperties = rootProject.file("version.properties")
val versionPropertiesFile = Properties()
versionPropertiesFile.load(FileInputStream(versionProperties))

var appVersionCode = Integer.parseInt(versionPropertiesFile["versionCode"].toString())
var major = Integer.parseInt(versionPropertiesFile["major"].toString())
var minor = Integer.parseInt(versionPropertiesFile["minor"].toString())
var patch = Integer.parseInt(versionPropertiesFile["patch"].toString())
var libVersion = "$major.$minor.$patch"

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

android {
    namespace = "com.isakaro.kwik"
    compileSdk = libs.versions.sdk.target.get().toInt()
    buildToolsVersion = libs.versions.build.tools.version.get()

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    implementation(libs.bundles.app)
    implementation(libs.bundles.app.theming)
    implementation(libs.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(libs.bundles.test)
    dokkaPlugin(libs.android.documentation.plugin)
    coreLibraryDesugaring(libs.desugaring)
}

val mavenUsername = secretsProperties["mavenUsername"].toString()
val mavenToken = secretsProperties["mavenToken"].toString()
val signingKeyId = secretsProperties["signing.keyId"].toString()
val signingPassword = secretsProperties["signing.password"].toString()
val signingKey = secretsProperties["signing.key"].toString()

tasks.register<Zip>("bundleForMavenCentral") {
    group = "publishing"
    description = "Creates a bundle zip file for uploading to Maven Central"
    archiveFileName.set("central-bundle.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
    dependsOn("publishToMavenLocal")

    from(layout.buildDirectory.dir("publications")) {
        include("**/*.pom")
        include("**/*.jar")
        include("**/*.aar")
        include("**/*.module")
        include("**/*.asc")
    }
}

tasks.register<Exec>("uploadToMavenCentral") {
    group = "publishing"
    description = "Uploads the bundle to Maven Central"
    dependsOn("bundleForMavenCentral")

    commandLine(
        "curl",
        "--request", "POST",
        "--verbose",
        "--header", "Authorization: Bearer " + "${mavenUsername}:${mavenToken}".toBase64(),
        "--form", "bundle=@${layout.buildDirectory.file("distributions/central-bundle.zip").get()}",
        "https://central.sonatype.com/api/v1/publisher/upload"
    )
}

fun String.toBase64(): String {
    return Base64.getEncoder().encodeToString(this.toByteArray())
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.isakaro"
            artifactId = "kwik.ui"
            version = libVersion

            pom {
                name = "KwikUI"
                description = Meta.Descripton
                url = "https://github.com/${Meta.GithubRepository}"
                inceptionYear = "2025"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "isakaro"
                        name = "Isakaro"
                        email = "ganza@isakaro.com"
                        organizationUrl = "https://isakaro.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/${Meta.GithubRepository}.git"
                    developerConnection = "scm:git:ssh://git@github.com/${Meta.GithubRepository}.git"
                    url = "https://github.com/${Meta.GithubRepository}"
                }
                issueManagement {
                    system.set("GitHub")
                    url = "https://github.com/${Meta.GithubRepository}/issues"
                }
            }
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["release"])
}

task("updateVersionCode") {
    doFirst {
        appVersionCode++
        versionPropertiesFile.setProperty("versionCode", "$appVersionCode")
    }
}

task("getVersion") {
    val type = properties["type"].toString()

    when(type) {
        "code" -> println("$appVersionCode")
        "name" -> println("$major.$minor.$patch")
    }
}

/*
 * [commitMessage] is passed as argument to this gradle task in command line
 * for example: gradle updateVersion -PcommitMessage="[major]"
 * patch will be incremented if no commitMessage is specified
 * */
tasks.register("updateVersion") {
    val commitMessage = properties["commitMessage"].toString()

    if (!commitMessage.contains("[skip-version-code]")) {
        dependsOn("updateVersionCode")
    }

    doFirst {
        if (!commitMessage.contains("[skip-version-name]")) {
            when {
                commitMessage.contains("[major]") -> {
                    major++; versionPropertiesFile.setProperty("major", "$major")
                    minor = 0; versionPropertiesFile.setProperty("minor", "$minor")
                    patch = 0; versionPropertiesFile.setProperty("patch", "$patch")
                }

                commitMessage.contains("[minor]") -> {
                    if (minor + 1 > 999) {
                        major++; versionPropertiesFile.setProperty("major", "$major")
                        minor = 0; versionPropertiesFile.setProperty("minor", "$minor")
                        patch = 0; versionPropertiesFile.setProperty("patch", "$patch")
                    } else {
                        minor++; versionPropertiesFile.setProperty("minor", "$minor")
                        patch = 0; versionPropertiesFile.setProperty("patch", "$patch")
                    }
                }

                else -> {
                    if (patch + 1 > 999) {
                        minor++; versionPropertiesFile.setProperty("minor", "$minor")
                        patch = 0; versionPropertiesFile.setProperty("patch", "$patch")
                    } else {
                        patch++; versionPropertiesFile.setProperty("patch", "$patch")
                    }
                }
            }
        }
        versionPropertiesFile.store(
            FileOutputStream(rootProject.file("version.properties")),
            "v$major.$minor.$patch | code=$appVersionCode"
        )
    }

    doLast {
        println("$major.$minor.$patch")
    }
}

