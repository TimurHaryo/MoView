import release.CoreDependencies
import release.UiDependencies

apply(from = "../../buildSrc/commons.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}

android {
    namespace = NameSpace.featureInitial
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
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.fragment)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.navigationUiKtx)
    implementation(CoreDependencies.navigationFragmentKtx)
    implementation(UiDependencies.material)
    implementation(UiDependencies.constraintLayout)
    implementation(UiDependencies.splashScreen)
}
