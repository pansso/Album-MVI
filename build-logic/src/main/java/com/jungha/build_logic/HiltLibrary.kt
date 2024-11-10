package com.jungha.build_logic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.applyHiltLibrary() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    with(pluginManager) {
        apply("kotlin-kapt")
        apply("dagger.hilt.android.plugin")
    }
    androidExtension.apply {
        dependencies {
            "implementation"(libs.findLibrary("hilt-android").get())
            "kapt"(libs.findLibrary("hilt-android-compiler").get())
        }
    }
}