import extension.projectsImplementation
import release.CoreDependencies
import release.UiDependencies

apply(from = "../buildSrc/commons.gradle")
apply(plugin = "dagger.hilt.android.plugin")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.plugin.serialization")
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
    projectsImplementation(FeaturesModules.projectFeatures)
    implementation(project(Modules.commonAndroid))
    implementation(project(Modules.uikit))
    implementation(project(Modules.navigation))
    implementation(CoreDependencies.appCompat)
    implementation(CoreDependencies.navigationFragmentKtx)
    implementation(CoreDependencies.threeTenABP)
    implementation(CoreDependencies.hiltAndroid)
    implementation(CoreDependencies.protoDataStore)
    implementation(CoreDependencies.kotlinCollectionsImmutable)
    implementation(CoreDependencies.kotlinSerializationJson)
    implementation(UiDependencies.constraintLayout)
    implementation(UiDependencies.splashScreen)
    kapt(CoreDependencies.hiltCompiler)
}

kapt {
    correctErrorTypes = true
}
