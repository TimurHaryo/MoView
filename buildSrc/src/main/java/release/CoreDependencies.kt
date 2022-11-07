package release

object CoreDependencies {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragment}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
}
