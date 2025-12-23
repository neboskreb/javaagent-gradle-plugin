package io.github.neboskreb.javaagent.impl

import io.github.neboskreb.javaagent.JavaForkOptionsConfigurer
import io.github.neboskreb.javaagent.JavaagentPlugin.Companion.CONFIGURATION_NAME
import io.github.neboskreb.javaagent.JavaagentPlugin.Companion.TEST_CONFIGURATION_NAME
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.testing.Test

class JavaagentJavaPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.withId("application") {
            project.logger.info("JavaagentPlugin: Found Java application in project '{}'", project.name)
            configureRun(project)
            configureTest(project)
        }
        project.plugins.withId("java-library") {
            project.logger.info("JavaagentPlugin: Found Java library in project '{}'", project.name)
            configureTest(project)
        }
    }

    private fun configureTest(project: Project) {
        // configure the run task to use the `javaagent` flag pointing to the dependency stored in the local Maven repository
        project.tasks.named(JavaPlugin.TEST_TASK_NAME, Test::class.java).configure { task ->
            project.logger.info("JavaagentPlugin: Configuring task ':{}:{}'", project.name, task.name)
            val javaagentTestConfiguration = project.configurations.named(TEST_CONFIGURATION_NAME)
            JavaForkOptionsConfigurer.configureJavaForkOptions(
                task,
                javaagentTestConfiguration.map { configuration ->
                    configuration.files
                },
            )
        }
    }

    private fun configureRun(project: Project) {
        // configure the run task to use the `javaagent` flag pointing to the dependency stored in the local Maven repository
        project.tasks.named(ApplicationPlugin.TASK_RUN_NAME, JavaExec::class.java).configure { task ->
            project.logger.info("JavaagentPlugin: Configuring task ':{}:{}'", project.name, task.name)
            val javaagentConfiguration = project.configurations.named(CONFIGURATION_NAME)
            JavaForkOptionsConfigurer.configureJavaForkOptions(
                task,
                javaagentConfiguration.map { configuration ->
                    configuration.files
                },
            )
        }
    }
}