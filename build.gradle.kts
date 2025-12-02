import org.jreleaser.model.Active
import java.time.LocalDate

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.12.3" apply false
    id("org.jetbrains.kotlin.android") version "2.2.21" apply false
    id("com.google.devtools.ksp") version "2.3.3" apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jreleaser)
    base
}

// Read version from version.properties
val versionProperties = java.util.Properties()
file("version.properties").inputStream().use { versionProperties.load(it) }
val major = versionProperties["major"].toString().toInt()
val minor = versionProperties["minor"].toString().toInt()
val patch = versionProperties["patch"].toString().toInt()
val projectVersion = "$major.$minor.$patch"

version = projectVersion
group = "com.isakaro"

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {

    }
}

jreleaser {
    gitRootSearch = true

    project {
        description = ("A Jetpack Compose library for building UI components.")
        authors = (listOf("Isakaro"))
        license = ("Apache-2.0")
        links {
            homepage = ("https://github.com/isakaro/kwik-ui-android")
            bugTracker = ("https://github.com/isakaro/kwik-ui-android/issues")
            contact = ("https://isakaro.com")
        }
        inceptionYear = ("2025")
        vendor = ("Isakaro")
        copyright = ("Copyright (c) ${LocalDate.now().year} Isakaro")
    }

    release {
        github {
            repoOwner = ("isakaro")
            name = ("kwik-ui-android")
            overwrite = true
            skipTag = (false)
            changelog {
                formatted = Active.ALWAYS
                preset = ("conventional-commits")
            }
            commitAuthor {
                name = ("Isakaro")
                email = ("ganza@isakaro.com")
            }
        }
    }

    signing {
        active = Active.ALWAYS
        armored = true
        mode = (org.jreleaser.model.Signing.Mode.MEMORY)
        verify = true
    }

    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active = Active.ALWAYS
                    url = ("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository(file("kwik/build/staging-deploy").absolutePath)
                    applyMavenCentralRules = true
                    sign = true
                    checksums = true
                    sourceJar = true
                    javadocJar = true
                    verifyPom = false
                }
            }
        }
    }
}