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
    ":common-android",
    ":common-kotlin",
    ":data:dto",
    ":data:local",
    ":data:local-interactor",
    ":data:remote",
    ":data:remote-interactor",
    ":data:repository",
    ":data:repository-interactor",
    ":domain:wrapper",
    ":domain:usecase",
    ":navigation",
    ":uikit",
    ":feature:feature-item",
    ":feature:feature-helper",
    ":feature:initial",
    ":feature:home",
    ":feature:review",
    ":feature:collection"
)
