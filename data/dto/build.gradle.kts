import release.CoreDependencies
import release.NetworkDependencies

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(Modules.commonKotlin))
    implementation(NetworkDependencies.gson)
    implementation(NetworkDependencies.retrofit)
    implementation(CoreDependencies.roomCommon)
    ksp(CoreDependencies.roomCompiler)
}
