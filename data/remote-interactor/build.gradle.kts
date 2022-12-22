import release.CoreDependencies
import release.NetworkDependencies

apply(from = "../../buildSrc/commons.gradle")
apply(plugin = "dagger.hilt.android.plugin")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = NameSpace.remoteInteractor
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
    implementation(project(Modules.remote))
    implementation(project(Modules.dto))
    implementation(NetworkDependencies.retrofit)
    implementation(NetworkDependencies.gsonConverter)
    implementation(NetworkDependencies.okhttp3)
    implementation(CoreDependencies.hiltAndroid)
    kapt(CoreDependencies.hiltCompiler)
}

kapt {
    correctErrorTypes = true
}
