apply(from = "../buildSrc/commons.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = NameSpace.uikit
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
