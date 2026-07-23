rootProject.name = "example-projects"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    includeBuild("..")

    plugins {
        id("com.android.application").version("9.3.1")
        id("com.android.library").version("9.3.1")
        id("de.mannodermaus.android-junit5").version("2.0.1")
        id("io.github.neboskreb.javaagent").version("1.2.8-SNAPSHOT")
    }
}

includeBuild("..")

include("java-app", "java-library", "android-app", "android-library")
