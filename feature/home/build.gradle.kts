import extension.androidTestsImplementation
import extension.testsImplementation
import release.CoreDependencies
import release.UiDependencies
import test.TestDependencies

apply(from = "../../buildSrc/commons.gradle")
apply(plugin = "dagger.hilt.android.plugin")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = NameSpace.featureHome
    defaultConfig {
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFile(AppConfig.proguardConsumerRules)
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(Modules.commonAndroid))
    implementation(project(Modules.featureItem))
    implementation(project(Modules.featureHelper))
    implementation(project(Modules.domainUseCase))
    implementation(project(Modules.domainWrapper))
    implementation(project(Modules.uikit))
    implementation(project(Modules.navigation))
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.fragment)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.navigationUiKtx)
    implementation(CoreDependencies.navigationFragmentKtx)
    implementation(CoreDependencies.hiltAndroid)
    implementation(UiDependencies.material)
    implementation(UiDependencies.recyclerView)
    implementation(UiDependencies.shimmerView)
    implementation(UiDependencies.constraintLayout)
    kapt(CoreDependencies.hiltAndroidCompiler)
    testsImplementation(TestDependencies.testLibraries)
    androidTestsImplementation(TestDependencies.androidTestLibraries)
}
