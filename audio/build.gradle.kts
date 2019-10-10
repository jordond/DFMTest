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

    api(Libs.kotlin)

    implementation(Libs.coreKtx)
    implementation(Libs.constraintlayout)
    implementation(Libs.lifecycleExtensions)
    implementation(Libs.lifecycleLiveData)
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.material)
    implementation(Libs.groupie)
    implementation(Libs.groupieAndroidExtensions)
}