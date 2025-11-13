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
