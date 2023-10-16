plugins {
    `android-library`
    `kotlin-android`
}
apply<MainGradlePlugin>()
android {
    namespace = "com.roberto_product.local"
}

dependencies {
    hilt()
    models()
    room()
    preference()
    sqlite()
    securityCypto()
}