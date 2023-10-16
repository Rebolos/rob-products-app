import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    implementation("com.android.tools.build:gradle:7.3.0")
    implementation("com.squareup:javapoet:1.13.0") // <-- added this
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
configurations.all {
    resolutionStrategy.eachDependency {
        when (requested.name) {
            "javapoet" -> useVersion("1.13.0")
        }
    }
}