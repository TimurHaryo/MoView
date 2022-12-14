object Modules {
    const val app = ":app"
    const val commonAndroid = ":common-android"
    const val commonKotlin = ":common-kotlin"
    const val navigation = ":navigation"
    const val uikit = ":uikit"
}

object FeaturesModules {
    private const val featureInitial = ":feature:initial"
    private const val featureHome = ":feature:home"
    private const val featureReview = ":feature:review"
    private const val featureCollection = ":feature:collection"

    val projectFeatures = listOf(
        featureInitial,
        featureHome,
        featureReview,
        featureCollection
    )
}
