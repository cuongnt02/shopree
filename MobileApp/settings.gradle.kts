pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "MobileApp"
include(
    ":app",
    ":core:model",
    ":core:ui",
    ":core:network",
    ":core:database",
    ":core:datastore",
    ":feature:auth",
    ":feature:catalog",
    ":feature:cart",
    ":feature:checkout",
    ":feature:orders",
    ":feature:vendor"
)
