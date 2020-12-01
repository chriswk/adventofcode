import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version("1.4.20")
}

repositories {
    jcenter()
    maven(url = "https://packages.confluent.io/maven/")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    testImplementation("org.assertj:assertj-core:3.18.1")
    testImplementation("org.apache.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.apache.junit.jupiter:junit-jupiter-engine:5.7.0")
    implementation(kotlin("stdlib-jdk8"))
}
tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}