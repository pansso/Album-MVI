plugins {
    id("com.android.application")
    id("com.album.application")
}

android {
    namespace = "com.jungha.album"

    defaultConfig {
        applicationId = "com.jungha.album"
        targetSdk = libs.versions.targetSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":core:domain"))
    implementation(project(":feature:main"))
}