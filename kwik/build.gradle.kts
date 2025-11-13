import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("maven-publish")
    id("signing")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka)
}

object Meta {
    const val Descripton = "A Jetpack Compose library for building UI components."
    const val License = "Apache-2.0"
    const val GithubRepository = "isakaro/kwik-ui-android"
}

val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretsProperties = Properties()
if (secretsPropertiesFile.exists()) {
    secretsProperties.load(FileInputStream(secretsPropertiesFile))
}

val versionProperties = rootProject.file("version.properties")
val versionPropertiesFile = Properties()
versionPropertiesFile.load(FileInputStream(versionProperties))

var appVersionCode = Integer.parseInt(versionPropertiesFile["versionCode"].toString())
var major = Integer.parseInt(versionPropertiesFile["major"].toString())
var minor = Integer.parseInt(versionPropertiesFile["minor"].toString())
var patch = Integer.parseInt(versionPropertiesFile["patch"].toString())
var libVersion = "$major.$minor.$patch"

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
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.bundles.app)
    implementation(libs.bundles.app.theming)
    implementation(libs.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(libs.bundles.test)
    dokkaPlugin(libs.dokka)
    coreLibraryDesugaring(libs.desugaring)
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
    val signingKey = providers.environmentVariable("GPG_PRIVATE_KEY")
    val signingPassword = providers.environmentVariable("GPG_PASSWORD")
    if (signingKey.isPresent && signingPassword.isPresent) {
        useInMemoryPgpKeys(signingKey.get(), signingPassword.get())
        sign(publishing.publications)
    }
}

tasks.register("updateVersionCode") {
    doFirst {
        appVersionCode++
        versionPropertiesFile.setProperty("versionCode", "$appVersionCode")
    }
}

tasks.register("getVersion") {
    val type = properties["type"].toString()

    when (type) {
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

