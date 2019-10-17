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
    implementation(Libs.kotlin)
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)

    /* DFM */
    implementation(Libs.playCore)

    implementation(Libs.navigationFragment)
    implementation(Libs.navigationUi)
    implementation(Libs.appcompat)
    implementation(Libs.coreKtx)
    implementation(Libs.constraintlayout)
    implementation(Libs.lifecycleExtensions)
    implementation(Libs.lifecycleLiveData)
    implementation(Libs.lifecycleViewModel)

    implementation(Libs.groupie)
    implementation(Libs.groupieAndroidExtensions)

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