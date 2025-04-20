plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("maven-publish")
    id("signing")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.documentation.plugin)
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
        create<MavenPublication>("release") {
            groupId = project.property("GROUP_ID") as String
            artifactId = project.property("ARTIFACT_ID") as String
            version = project.property("VERSION_NAME") as String

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name = "KwikUI"
                description = "A Jetpack Compose library for building UI components."
                url = "http://www.example.com/library"
                properties = mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                )
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
                    connection = "scm:git:git@github.com/isakaro/kwik-ui-android.git"
                    developerConnection = "scm:git:ssh://github.com/isakaro/kwik-ui-android.git"
                    url = "https://github.com/isakaro/kwik-ui-android.git"
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("ossrhUsername") as String?
                password = project.findProperty("ossrhPassword") as String?
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}