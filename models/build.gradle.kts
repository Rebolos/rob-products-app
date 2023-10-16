plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()
android {
    namespace = "com.rob_products.models"
}

dependencies {
    hilt()
}
