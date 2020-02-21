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
    implementation(project(Modules.common))

    implementation(Libs.kotlin_stdlib_jdk7)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_coroutines_android)

    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_runtime_ktx)

    implementation(Libs.timberkt)
}
