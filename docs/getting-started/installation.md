# Installation
InterfaceOfOurOwn is distributed via Jitpack only. Include jitpack as a source repo like so:

=== "Groovy (build.gradle)"

    ```groovy
    allprojects {
        repositories {
            // ...
            maven { url "https://jitpack.io" }
        }
    }
    ```

=== "Kotlin DSL (build.gradle.kts)"

    ```kotlin
    allprojects {
        repositories {
            // ...
            maven { setUrl("https://jitpack.io") }
        }
    }
    ```

Then add the dependency to your module's buildscript like so:

=== "Groovy (build.gradle)"

    ```groovy
    implementation 'com.github.soblemprolved:interfaceofourown:0.3.0'
    ```

=== "Kotlin DSL (build.gradle.kts)"

    ```kotlin
    implementation("com.github.soblemprolved:interfaceofourown:0.3.0")
    ```

And that's it! You're good to go.
