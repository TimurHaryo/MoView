import release.CoreDependencies

apply(from = "../buildSrc/commons.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}

android {
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
}

dependencies {
    implementation(CoreDependencies.navigationUiKtx)
}
