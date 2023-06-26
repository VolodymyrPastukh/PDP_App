pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "PDP App"
include(":app")
include(":modules:data")
include(":modules:utils")
include(":modules:components")
include(":appcompose")
include(":modules:baseUi")
