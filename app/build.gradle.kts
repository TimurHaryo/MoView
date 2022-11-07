import extension.androidTestImplementation
import extension.testImplementation
import release.CoreDependencies
import release.UiDependencies
import test.TestDependencies

apply(from = "../buildSrc/commons.gradle")
apply(plugin = "dagger.hilt.android.plugin")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
}

android {
    defaultConfig {
        applicationId = AppConfig.namespace
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
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

    flavorDimensions.add(AppConfig.dimension)
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"
            dimension = AppConfig.dimension
        }

        create("production") {
            dimension = AppConfig.dimension
        }
    }
}

dependencies {
    implementation(project(Modules.common))
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.fragment)
    implementation(CoreDependencies.fragmentKtx)
    implementation(CoreDependencies.hiltAndroid)
    implementation(CoreDependencies.navigationUiKtx)
    kapt(CoreDependencies.hiltCompiler)
    implementation(UiDependencies.splashScreen)
    implementation(UiDependencies.material)
    implementation(UiDependencies.constraintLayout)
    testImplementation(TestDependencies.testLibraries)
    androidTestImplementation(TestDependencies.androidTestLibraries)
}

kapt {
    correctErrorTypes = true
}
