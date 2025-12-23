package com.ryandens.javaagent.impl

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.ryandens.javaagent.JavaForkOptionsConfigurer
import com.ryandens.javaagent.JavaagentPlugin.Companion.TEST_CONFIGURATION_NAME
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaagentAndroidTestPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val logger = project.logger

        // We need to act on the AGP extension, but we don't know if it's
        // the app or library plugin. So we check for both.
        // The `project.plugins.withId` block ensures our code runs after AGP is applied.
        project.plugins.withId("com.android.application") {
            logger.info("JavaagentPlugin: Found Android application in project '{}'", project.name)
            val androidComponents = project.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)
            configureUnitTests(project, androidComponents)
        }
        project.plugins.withId("com.android.library") {
            logger.info("JavaagentPlugin: Found Android library in project '{}'", project.name)
            val androidComponents = project.extensions.getByType(LibraryAndroidComponentsExtension::class.java)
            configureUnitTests(project, androidComponents)
        }
    }

    private fun configureUnitTests(project: Project, androidComponents: AndroidComponentsExtension<*, *, *>) {
        // Use onVariants to configure variants as they are created.
        // This is the correct, modern way to interact with AGP.
        androidComponents.onVariants(androidComponents.selector().all()) { variant ->
            // Safely get the name of the unit test task for this variant
            @Suppress("DEPRECATION") val unitTestComponent = variant.unitTest
            if (unitTestComponent != null) {
                unitTestComponent.configureTestTask { task ->
                    project.logger.info("JavaagentPlugin: Configuring task ':{}:{}'", project.name, task.name)
                    // Now you can configure the task.
                    // This block is executed only if the task is needed for the build.
                    val javaagentTestConfiguration = project.configurations.named(TEST_CONFIGURATION_NAME)
                    JavaForkOptionsConfigurer.configureJavaForkOptions(
                        task,
                        javaagentTestConfiguration.map { configuration ->
                            configuration.files
                        },
                    )
                }
            }
        }
    }
}
