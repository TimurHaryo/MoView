import release.CoreDependencies

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(Modules.commonKotlin))
    implementation(project(Modules.local))
    implementation(project(Modules.remote))
    implementation(project(Modules.repository))
    implementation(project(Modules.dto))
    implementation(project(Modules.domainWrapper))
    implementation(CoreDependencies.coroutinesCore)
    implementation(CoreDependencies.coroutinesAndroid)
    implementation(CoreDependencies.hilt)
    kapt(CoreDependencies.hiltCompiler)
}

kapt {
    correctErrorTypes = true
}
