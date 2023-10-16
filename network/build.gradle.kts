plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()
android {
    namespace = "com.example.network"

}

dependencies {
    gson()
    common()
    hilt()
    models()
}
