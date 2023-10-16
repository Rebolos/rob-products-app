pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "rob-products-app"
include(":app")
include(":commonStyles")
include(":common")
include(":network")
include(":data")
include(":domain")
include(":local")
include(":models")
