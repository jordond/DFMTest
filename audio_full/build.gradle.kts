import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id(BuildPlugins.dynamicFeature)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(App.compileSdk)

    defaultConfig {
        minSdkVersion(App.minSdk)
        targetSdkVersion(App.targetSdk)
    }

    compileOptions {
        sourceCompatibility = App.javaVersion
        targetCompatibility = App.javaVersion
    }

    kotlinOptions {
        (this as? KotlinJvmOptions)?.jvmTarget = App.javaVersion.toString()
    }
}

androidExtensions { isExperimental = true }

dependencies {
    implementation(project(Modules.app))
    implementation(project(Modules.audioBase))
    implementation(project(Modules.common))

    implementation(Libs.kotlin_stdlib_jdk7)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_coroutines_android)

    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)
    implementation(Libs.appcompat)
    implementation(Libs.androidx_core_core_ktx)
    implementation(Libs.constraintlayout)
    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_runtime_ktx)
    implementation(Libs.material)
    implementation(Libs.groupie)
    implementation(Libs.groupie_kotlin_android_extensions)

    implementation(Libs.timberkt)
}