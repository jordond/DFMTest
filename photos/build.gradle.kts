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
    }

    compileOptions {
        sourceCompatibility = App.javaVersion
        targetCompatibility = App.javaVersion
    }

    kotlinOptions {
        (this as? KotlinJvmOptions)?.jvmTarget = App.javaVersion.toString()
    }

    viewBinding.isEnabled = true
}

androidExtensions {
    isExperimental = true
}

dependencies {
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
    implementation("io.coil-kt:coil:0.9.5")
    implementation("com.github.florent37:inline-activity-result-kotlin:1.0.3")
    implementation("com.mikhaellopez:circularimageview:4.2.0")

    implementation(Libs.google_photos_library_client)
    implementation(Libs.play_services_auth)

    implementation(Libs.timberkt)
}
