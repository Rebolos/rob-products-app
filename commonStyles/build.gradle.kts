plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}
apply<MainGradlePlugin>()
android {
    namespace = "com.rob_products.commonstyles"
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    androidLibs()
    hilt()
}
