package io.github.neboskreb.javaagent

import io.github.neboskreb.javaagent.impl.JavaagentAndroidTestPlugin
import io.github.neboskreb.javaagent.impl.JavaagentJavaPlugin
import io.github.neboskreb.javaagent.utils.JavaagentVersionUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaagentPlugin : Plugin<Project> {
    companion object {
        const val CONFIGURATION_NAME = "javaagent"
        const val TEST_CONFIGURATION_NAME = "testJavaagent"
    }

    override fun apply(project: Project) {
        project.configurations.register(CONFIGURATION_NAME) { configuration ->
            configuration.isCanBeResolved = false
            configuration.isCanBeConsumed = false
            configuration.description = "Declares Java agent dependencies."
        }

        project.configurations.register(TEST_CONFIGURATION_NAME) { configuration ->
            configuration.isCanBeResolved = false
            configuration.isCanBeConsumed = false
            configuration.description = "Declares Java agent dependencies for unit tests."
            val javaagentConfiguration = project.configurations.named(CONFIGURATION_NAME)
            configuration.extendsFrom(javaagentConfiguration.get())
        }


        JavaagentJavaPlugin().apply(project)
        JavaagentAndroidTestPlugin().apply(project)
    }
}
