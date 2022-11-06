import extension.androidTestImplementation
import extension.testImplementation
import release.CoreDependencies
import release.UiDependencies
import test.TestDependencies

apply(from = "../buildSrc/commons.gradle")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
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
    implementation(UiDependencies.material)
    implementation(UiDependencies.constraintLayout)
    implementation(UiDependencies.okLayoutInflater)
    testImplementation(TestDependencies.testLibraries)
    androidTestImplementation(TestDependencies.androidTestLibraries)
}
