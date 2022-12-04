import release.CoreDependencies
import release.NetworkDependencies
import release.UiDependencies

apply(from = "../buildSrc/commons.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = NameSpace.common
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
    implementation(UiDependencies.material)
    implementation(CoreDependencies.fragment)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.coroutinesCore)
    implementation(CoreDependencies.coroutinesAndroid)
    implementation(NetworkDependencies.retrofit)
}
