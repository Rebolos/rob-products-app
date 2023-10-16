plugins {
    `android-library`
    `kotlin-android`
}
apply<MainGradlePlugin>()
android {
    namespace = "com.rob_product_domain"
}

dependencies {
    hilt()
    data()
    common()
}
