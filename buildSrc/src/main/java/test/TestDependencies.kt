package test

object TestDependencies {
    private const val junit = "junit:junit:${Versions.junit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val androidTestLibraries = listOf(extJUnit, espressoCore)

    val testLibraries = listOf(junit)
}
