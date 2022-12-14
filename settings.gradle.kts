import java.net.URI

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}

rootProject.name = "MoView"

include(
    ":app",
    ":common",
    ":common-kotlin",
    ":navigation",
    ":uikit",
    ":feature:initial",
    ":feature:home",
    ":feature:review",
    ":feature:collection"
)
