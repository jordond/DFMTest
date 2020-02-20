object RootPlugins {
    const val androidGradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
}

object BuildPlugins {

    const val library = "com.android.library"
    const val application = "com.android.application"

    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val dynamicFeature = "com.android.dynamic-feature"

    const val kotlinSafeArgs = "androidx.navigation.safeargs.kotlin"
}