object Modules {
    const val app = ":app"
    const val commonAndroid = ":common-android"
    const val commonKotlin = ":common-kotlin"
    const val dto = ":data:dto"
    const val local = ":data:local"
    const val localInteractor = ":data:local-interactor"
    const val remote = ":data:remote"
    const val remoteInteractor = ":data:remote-interactor"
    const val repository = ":data:repository"
    const val repositoryInteractor = ":data:repository-interactor"
    const val domainWrapper = ":domain:wrapper"
    const val domainUseCase = ":domain:usecase"
    const val featureItem = ":feature:feature-item"
    const val featureHelper = ":feature:feature-helper"
    const val navigation = ":navigation"
    const val uikit = ":uikit"
}

object FeaturesModules {
    const val featureInitialContract = ":feature:initial-contract"
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
