import extension.androidTestImplementation
import extension.testImplementation
import release.CoreDependencies
import release.UiDependencies
import test.TestDependencies

apply(from = "../../buildSrc/commons.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
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
    implementation(project(Modules.common))
    implementation(project(Modules.uikit))
    implementation(project(Modules.navigation))
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.fragment)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.navigationUiKtx)
    implementation(CoreDependencies.navigationFragmentKtx)
    implementation(UiDependencies.material)
    implementation(UiDependencies.constraintLayout)
    testImplementation(TestDependencies.testLibraries)
    androidTestImplementation(TestDependencies.androidTestLibraries)
}
