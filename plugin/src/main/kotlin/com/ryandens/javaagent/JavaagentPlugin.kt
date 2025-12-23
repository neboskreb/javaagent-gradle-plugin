package com.ryandens.javaagent

import com.ryandens.javaagent.impl.JavaagentAndroidTestPlugin
import com.ryandens.javaagent.impl.JavaagentJavaPlugin
import com.ryandens.javaagent.utils.JavaagentVersionUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaagentPlugin : Plugin<Project> {
    companion object {
        const val CONFIGURATION_NAME = "javaagent"
        const val TEST_CONFIGURATION_NAME = "testJavaagent"
    }

    override fun apply(project: Project) {
        project.configurations.register(CONFIGURATION_NAME) { configuration ->
            // we expect javaagents to come as shaded JARs
            configuration.isTransitive = false
        }

        project.configurations.register(TEST_CONFIGURATION_NAME) { configuration ->
            // we expect javaagents to come as shaded JARs
            configuration.isTransitive = false
            val javaagentConfiguration = project.configurations.named(CONFIGURATION_NAME)
            configuration.extendsFrom(javaagentConfiguration.get())
            configuration.resolutionStrategy { strategy ->
                strategy.eachDependency { dep ->
                    if (dep.requested.version.isNullOrBlank()) {
                        dep.useVersion(
                            JavaagentVersionUtil.versionFromDependencyAndConfiguration(
                                dep.requested,
                                project.configurations.named("testRuntimeClasspath"),
                            ),
                        )
                    }
                }
            }
        }


        JavaagentJavaPlugin().apply(project)
        JavaagentAndroidTestPlugin().apply(project)
    }
}
