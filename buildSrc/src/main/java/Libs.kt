import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Libs {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // Core-KTX
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val materialDesign =
        "com.google.android.material:material:${Versions.materialDesignVersion}"
    const val coordinatorLayout =
        "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayoutVersion}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
    const val dataBinding = "com.android.databinding:compiler:${Versions.dataBinding}"

    const val flowBinding =
        "io.github.reactivecircus.flowbinding:flowbinding-android:${Versions.flowBinding}"
    const val flowBindingCore =
        "io.github.reactivecircus.flowbinding:flowbinding-core:${Versions.flowBinding}"
    const val flowBindingAppcompat =
        "io.github.reactivecircus.flowbinding:flowbinding-appcompat:${Versions.flowBinding}"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutineGoogleServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationFragmentUI =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    const val sqlcipher = "net.zetetic:android-database-sqlcipher:${Versions.sqlChipherVersion}"
    const val sqlite = "androidx.sqlite:sqlite:${Versions.sqliteVersion}"

    // PreferenceManager
    val preference = "androidx.preference:preference-ktx:${Versions.preferenceManagerVersion}"

    const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCryptoVersion}"

    // https://proandroiddev.com/gradle-dependency-management-with-kotlin-94eed4df9a28
    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"

    const val archExtensionsCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.archCompVersion}"
    const val archRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.archCompVersion}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.archCompVersion}"
    const val archLifeCycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.archCompVersion}"
    const val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archCompVersion}"
    const val saveStateHandle =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.archCompVersion}"
    const val selectionRecyleView =
        "androidx.recyclerview:recyclerview-selection:${Versions.recycleViewSelectionVersion}"

    const val insetter = "dev.chrisbanes.insetter:insetter:${Versions.insetterVersion}"
}

fun DependencyHandler.room() {
    implementation(Libs.roomRuntime)
    implementation(Libs.roomKtx)
    kapt(Libs.roomCompiler)
}

fun DependencyHandler.gson() {
    implementation(Libs.gson)
}

fun DependencyHandler.commonStyles() {
    implementation(project(":commonStyles"))
}

fun DependencyHandler.common() {
    implementation(project(":common"))
}

fun DependencyHandler.archictureKtx() {
    implementation(Libs.fragmentKtx)
    implementation(Libs.activityKtx)
}

fun DependencyHandler.hilt() {
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)
}

fun DependencyHandler.androidLibs() {
    implementation(Libs.coreKTX)
    kapt(Libs.dataBinding)
    implementation(Libs.appCompat)
    implementation(Libs.materialDesign)
    implementation(Libs.constraintLayout)
    implementation(Libs.swipeRefreshLayout)
    implementation(Libs.selectionRecyleView)
}

fun DependencyHandler.timber() {
    implementation(Libs.timber)
}

fun DependencyHandler.navigationComponent() {
    implementation(Libs.navigationFragmentUI)
    implementation(Libs.navigationFragmentKtx)
}
// fun DependencyHandler.kotlin() {
//    implementation(Libs.kotlinStdlib)
// }

fun DependencyHandler.network() {
    api(project(":network"))
}

fun DependencyHandler.models() {
    api(project(":models"))
}

fun DependencyHandler.domain() {
    implementation(project(":domain"))
}

fun DependencyHandler.local() {
    api(project(":local"))
}

fun DependencyHandler.preference() {
    implementation(Libs.preference)
}

fun DependencyHandler.sqlite() {
    implementation(Libs.sqlite)
    implementation(Libs.sqlcipher)
}

fun DependencyHandler.flowBinding() {
    implementation(Libs.flowBinding)
    implementation(Libs.flowBindingCore)
    implementation(Libs.flowBindingAppcompat)
}
fun DependencyHandler.securityCypto() {
    implementation(Libs.securityCrypto)
}

fun DependencyHandler.data() {
    api(project(":data"))
}

fun DependencyHandler.lifecycleKtx() {
    kapt(Libs.archExtensionsCompiler)
    implementation(Libs.archLifeCycleJava8)
    implementation(Libs.viewModelKtx)
    implementation(Libs.archRuntimeKtx)
}
