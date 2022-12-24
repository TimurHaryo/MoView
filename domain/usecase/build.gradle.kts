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
    implementation(project(Modules.repository))
    implementation(project(Modules.domainWrapper))
    implementation(project(Modules.featureItem))
    implementation(CoreDependencies.coroutinesCore)
    implementation(CoreDependencies.hilt)
    kapt(CoreDependencies.hiltCompiler)
}
