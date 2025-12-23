rootProject.name = "example-projects"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    includeBuild("..")

    plugins {
        id("com.android.application").version("8.13.2")
        id("com.android.library").version("8.13.2")
        id("de.mannodermaus.android-junit5").version("1.14.0.0")
        id("io.github.neboskreb.javaagent").version("1.2.7-SNAPSHOT")
    }
}

includeBuild("..")

include("java-app", "java-library", "android-app", "android-library")
