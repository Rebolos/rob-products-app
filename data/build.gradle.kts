plugins {
    `android-library`
    `kotlin-android`
}
apply<MainGradlePlugin>()
android {
    namespace = "com.rob_products_data"
}

dependencies {
    models()
    local()
    network()
    hilt()
    gson()
    common()
}