plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.rob_products_app"
    compileSdk = ProjectConfig.compileSdkVersion

    defaultConfig {
        applicationId = "com.rob_products_app"
        minSdk = ProjectConfig.minSdkVersion
        targetSdk = ProjectConfig.targetSdkVersion
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
//    kotlin()
    lifecycleKtx()
    commonStyles()
    archictureKtx()
    androidLibs()
    gson()
    domain()
    room()
    navigationComponent()
    hilt()
    common()
    timber()

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
