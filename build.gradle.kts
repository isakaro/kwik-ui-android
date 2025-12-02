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
    gitRootSearch.set(true)

    project {
        description.set("A Jetpack Compose library for building UI components.")
        authors.set(listOf("Isakaro"))
        license.set("Apache-2.0")
        links {
            homepage.set("https://github.com/isakaro/kwik-ui-android")
            bugTracker.set("https://github.com/isakaro/kwik-ui-android/issues")
            contact.set("https://isakaro.com")
        }
        inceptionYear.set("2025")
        vendor.set("Isakaro")
        copyright.set("Copyright (c) ${LocalDate.now().year} Isakaro")
    }

    release {
        github {
            repoOwner.set("isakaro")
            name.set("kwik-ui-android")
            overwrite.set(true)
            skipTag.set(false)
            changelog {
                formatted.set(Active.ALWAYS)
                preset.set("conventional-commits")
            }
            commitAuthor {
                name.set("Isakaro")
                email.set("ganza@isakaro.com")
            }
        }
    }

    signing {
        active.set(Active.ALWAYS)
        armored.set(true)
        mode.set(org.jreleaser.model.Signing.Mode.MEMORY)
        verify.set(true)
    }

    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active.set(Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository(file("kwik/build/staging-deploy").absolutePath)
                    applyMavenCentralRules.set(true)
                    sign.set(true)
                    checksums.set(true)
                    sourceJar.set(true)
                    javadocJar.set(true)
                    verifyPom.set(true)
                }
            }
        }
    }
}