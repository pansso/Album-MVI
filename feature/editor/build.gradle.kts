plugins {
    id("com.album.feature.library")
}

android {
    namespace = "com.jungha.editor"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.gson)
    implementation(libs.glide.compose)
    implementation(libs.glide.svg)
}