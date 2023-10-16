// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        jcenter()
    }
    dependencies {
        classpath(AndroidPlguin.androidPlugin)
        classpath(AndroidPlguin.kotlinPlugin)
        classpath(AndroidPlguin.crashlyticsGradlePlugin)
        classpath(AndroidPlguin.googlePlayPlugin)
        classpath(AndroidPlguin.navigationPlugin)
        classpath(AndroidPlguin.hiltPlugin)
        classpath("com.squareup:javapoet:1.13.0")
    }
}
configurations.all {
    resolutionStrategy.eachDependency {
        when (requested.name) {
            "javapoet" -> useVersion("1.13.0")
        }
    }
}
