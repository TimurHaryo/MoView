object Modules {
    const val app = ":app"
    const val common = ":common"
    const val navigation = ":navigation"
    const val uikit = ":uikit"
}

object FeaturesModules {
    private const val featureInitial = ":feature:initial"
    private const val featureHome = ":feature:home"
    private const val featureReview = ":feature:review"

    val projectFeatures = listOf(
        featureInitial,
        featureHome,
        featureReview
    )
}
