import release.CoreDependencies

apply(from = "../buildSrc/commons.gradle")
apply(plugin = "dagger.hilt.android.plugin")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = NameSpace.app
    defaultConfig {
        applicationId = NameSpace.app
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
    implementation(project(Modules.featureInitial))
    implementation(CoreDependencies.hiltAndroid)
    kapt(CoreDependencies.hiltCompiler)
}

kapt {
    correctErrorTypes = true
}
