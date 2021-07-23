import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.1"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("org.jsoup:jsoup:1.13.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("com.squareup.okhttp3:logging-interceptor")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
