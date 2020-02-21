import com.android.build.gradle.internal.dsl.DefaultConfig
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id(BuildPlugins.library)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(App.compileSdk)

    defaultConfig {
        minSdkVersion(App.minSdk)
        targetSdkVersion(App.targetSdk)

        versionCode = App.code
        versionName = App.name

        buildConfigField("FEATURE_MODULE_NAMES", Modules.dynamic.toSet())
        buildConfigField("FEATURE_MODULE_AUDIO_RAW", Modules.audioRaw)
        buildConfigField("FEATURE_MODULE_AUDIO_FULL", Modules.audioFull)
        buildConfigField("FEATURE_MODULE_TEST", Modules.testFeature)
    }

    compileOptions {
        sourceCompatibility = App.javaVersion
        targetCompatibility = App.javaVersion
    }

    kotlinOptions {
        (this as? KotlinJvmOptions)?.jvmTarget = App.javaVersion.toString()
    }
}

dependencies {
    implementation(Libs.kotlin_stdlib_jdk7)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_coroutines_android)

    /* DFM */
    implementation(Libs.com_google_android_play_core_ktx)

    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)
    implementation(Libs.appcompat)

    /* DFM */
    implementation(Libs.com_google_android_play_core_ktx)

    implementation(Libs.androidx_core_core_ktx)
    implementation(Libs.constraintlayout)
    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_viewmodel_savedstate)
    implementation(Libs.recyclerview)
    implementation(Libs.activity_ktx)
    implementation(Libs.fragment_ktx)

    implementation(Libs.groupie)
    implementation(Libs.groupie_kotlin_android_extensions)

    implementation(Libs.timberkt)
}

fun DefaultConfig.buildConfigField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}

fun DefaultConfig.buildConfigField(name: String, value: Set<String>) {
    // Generates String that holds Java String Array code
    val strValue = value.joinToString(prefix = "{", separator = ",", postfix = "}", transform = { "\"$it\"" })
    buildConfigField("String[]", name, strValue)
}