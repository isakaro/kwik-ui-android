// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    id("maven-publish")
    alias(libs.plugins.kotlin.compose) apply false
}

buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {

    }
}
