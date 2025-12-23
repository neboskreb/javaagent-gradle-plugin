plugins {
    id("build-logic.plugin-conventions")
}

val plugin: Configuration by configurations.creating

configurations {
    compileOnly {
        extendsFrom(plugin)
    }
    testImplementation {
        extendsFrom(plugin)
    }
}

tasks.named<PluginUnderTestMetadata>("pluginUnderTestMetadata") {
    // adds dependencies with the plugin configuration to the plugin classpath
    pluginClasspath.setFrom(pluginClasspath.plus(plugin.files))
    // avoid warnings
    dependsOn(tasks.compileKotlin)
    dependsOn(tasks.compileJava)
    dependsOn(tasks.processResources)
}


repositories {
    google()
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.13.2")

    testImplementation("com.android.application:com.android.application.gradle.plugin:8.13.2")
    testImplementation("com.android.library:com.android.library.gradle.plugin:8.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.apache.commons:commons-compress:1.28.0")
}

gradlePlugin {
    website.set("https://github.com/neboskreb/javaagent-gradle-plugin")
    vcsUrl.set("https://github.com/neboskreb/javaagent-gradle-plugin")
    plugins {
        create("JavaagentPlugin") {
            id = "io.github.neboskreb.javaagent"
            displayName = "Javaagent Plugin"
            description = "Automatically attaches javaagents to the Java and Android test tasks"
            implementationClass = "com.ryandens.javaagent.JavaagentPlugin"
            tags.set(listOf("javaagent", "instrumentation", "test"))
        }
    }
}
