package com.jungha.build_logic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.applyComposeLibrary() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    androidExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeCompiler").get().toString()
        }

        dependencies {
            "implementation"(libs.findLibrary("runtime-compose").get())
            "implementation"(libs.findLibrary("activity-compose").get())
            "implementation"(libs.findLibrary("material3-compose").get())
            "implementation"(libs.findLibrary("navigation-compose").get())
            "implementation"(libs.findLibrary("hilt-navigation-compose").get())
            "implementation"(libs.findLibrary("lifecycle-runtime-compose").get())
        }
    }
}