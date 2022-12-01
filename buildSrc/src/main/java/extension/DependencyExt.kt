package extension

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidTestsImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testsImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.projectsImplementation(list: List<String>) {
    list.forEach { project ->
        add("implementation", project(mapOf("path" to project)))
    }
}
