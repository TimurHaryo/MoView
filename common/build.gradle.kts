import release.CoreDependencies
import release.NetworkDependencies
import release.UiDependencies

apply(from = "../buildSrc/commons.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = AppConfig.namespace
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
}

dependencies {
    implementation(CoreDependencies.fragment)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.coroutinesCore)
    implementation(CoreDependencies.coroutinesAndroid)
    implementation(UiDependencies.okLayoutInflater)
    implementation(NetworkDependencies.retrofit)
}
