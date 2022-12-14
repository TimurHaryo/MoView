// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.7.20")
    }
}
plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("androidx.navigation.safeargs") version "2.4.2" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
    id("com.google.devtools.ksp") version "1.7.20-1.0.7" apply false
}
