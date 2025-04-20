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
    alias(libs.plugins.android.documentation.plugin)
}

val secretsProperties = rootProject.file("secrets.properties")
val secretsPropertiesFile = Properties()
secretsPropertiesFile.load(FileInputStream(secretsProperties))

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
    packaging {
        resources {
            excludes += "/META-INF/*"
        }
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
    dokkaPlugin(libs.android.documentation.plugin)
    coreLibraryDesugaring(libs.desugaring)
}

publishing {
    publications {
        create<MavenPublication>("kwikUiRelease") {
            groupId = "com.isakaro"
            artifactId = "kwik-ui"
            version = libVersion

            pom {
                name = "KwikUI"
                description = "A Jetpack Compose library for building UI components."
                url = "https://github.com/isakaro/kwik-ui-android"
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
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/isakaro/kwik-ui-android.git"
                    developerConnection = "scm:git:git@github.com/isakaro/kwik-ui-android.git"
                    url = "https://github.com/isakaro/kwik-ui-android"
                }
            }
        }
    }

    repositories {
        maven {
            name = "MavenCentral"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = secretsPropertiesFile["ossrhUsername"].toString()
                password = secretsPropertiesFile["ossrhPassword"].toString()
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["kwikUiRelease"])
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