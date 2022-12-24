import release.CoreDependencies
import release.NetworkDependencies

apply(from = "../../buildSrc/commons.gradle")
apply(plugin = "dagger.hilt.android.plugin")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    kotlin("kapt")
}

android {
    namespace = NameSpace.localInteractor
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
    implementation(project(Modules.local))
    implementation(project(Modules.dto))
    implementation(CoreDependencies.roomRuntime)
    implementation(CoreDependencies.roomKtx)
    implementation(NetworkDependencies.gson)
    implementation(CoreDependencies.hiltAndroid)
    kapt(CoreDependencies.hiltAndroidCompiler)
    ksp(CoreDependencies.roomCompiler)
}
