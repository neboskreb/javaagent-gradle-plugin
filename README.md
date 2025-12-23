# Javaagent Gradle Plugin

Gradle plugin for easy configuration of Java Agents.

Supports:
* Java application running/testing
* Java library testing
* Android application testing
* Android library testing

# The problem

From the perspective of the recent improvements in Java runtime security, Java Agents are seen as a possible attack vector.
Hence, the implicit attachment of Java Agent is currently discouraged, and in future might get forbidden. At the current
moment, developers will see a runtime warning similar to this if a Java Agent is attached dynamically:
```terminaloutput
Mockito is currently self-attaching to enable the inline-mock-maker. This will no longer work in future releases of the JDK. 
Please add Mockito as an agent to your build as described in Mockito's documentation: https://javadoc.io/doc/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html#0.3

WARNING: A Java agent has been loaded dynamically (C:\.gradle\caches\modules-2\files-2.1\net.bytebuddy\byte-buddy-agent\1.17.7\fbf3d6d649ed37fc9e9c59480a05be0a26e3c2da\byte-buddy-agent-1.17.7.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release
```


# The solution

The solution is to explicitly specify Java Agent(s) using `-javaagent:xxxx` command line which exist for this very purpose.

Javaagent Gradle Plugin allows you to easily specify which configurations should have which Java Agent attached.
For example, this Mockito test is configured to use Mockito's own Java Agent:
```kotlin
dependencies {
    testImplementation(platform("org.mockito:mockito-bom:$mokito_version"))
    testImplementation("org.mockito:mockito-core")
    testJavaagent("org.mockito:mockito-core")
    ...
}
```

# Setup

Configure the plugin in your `settigs.gradle.kts`:
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    plugins {
        id("io.github.neboskreb.javaagent").version("1.2.6")
    }
}
```


Apply the plugin in your `build.gradle.kts` and specify the configuration(s) and agent(s):
```kotlin
plugins {
    id("io.github.neboskreb.javaagent")
}

dependencies {
    // Attaching the Byte Buddy to the app:
    javaagent("net.bytebuddy:byte-buddy-agent:1.17.7")
    
    // Letting Mockito to use its agent for dynamic mocking:
    testImplementation(platform("org.mockito:mockito-bom:5.18.0"))
    testImplementation("org.mockito:mockito-core")
    testJavaagent("org.mockito:mockito-core")
}
```


## Android Support

Javaagent Gradle Plugin supports both Application and Library type of Android project. JUnit versions 5 and 6 are supported.

Simply apply plugin `io.github.neboskreb.javaagent` and add `testJavaagent` to your JUnit test dependencies:

```kotlin
plugins {
    id("com.android.application")
    id("io.github.neboskreb.javaagent")
}

dependencies {
    testImplementation(platform("org.mockito:mockito-bom:5.18.0"))
    testImplementation("org.mockito:mockito-core")
    testJavaagent("org.mockito:mockito-core")
    
    // Note that Android tests use Mockito-Android which does not require java agent
    androidTestImplementation("org.mockito:mockito-android:5.21.0")
    androidTestImplementation("org.mockito:mockito-junit-jupiter:5.21.0")
}
```
> [!NOTE]
> You don't need to add `androidTestJavaagent`.


# Examples
Feel free to load submodule [examples](./example-projects) with IntelliJ or Android Studio and poke around.


# Contributions
Pull requests are welcome! If you plan to open one, please first create an issue where you describe the problem/gap your contribution closes,
and tag the keeper(s) of this repo so they could get back to you with help.


# License
```
Copyright 2025 John Y. Pazekha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
See also the [full License text](./LICENSE).
