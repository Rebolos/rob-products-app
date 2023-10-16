plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}
apply<MainGradlePlugin>()
android {
    namespace = "com.rob_product_common"
//    compileSdk = ProjectConfig.compileSdkVersion
//
//    defaultConfig {
//        minSdk = ProjectConfig.minSdkVersion
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
    buildFeatures {
        dataBinding = true
    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_18
//        targetCompatibility = JavaVersion.VERSION_18
//    }
//    kotlinOptions {
//        jvmTarget = "18"
//    }
}

dependencies {
    androidLibs()
    lifecycleKtx()
    commonStyles()
    room()
    gson()
    hilt()
    archictureKtx()
    navigationComponent()
    timber()
    api(Libs.insetter)
    flowBinding()
}
