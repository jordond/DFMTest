plugins {
    id(BuildPlugins.dynamicFeature)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}

android {
    compileSdkVersion(App.compileSdk)

    defaultConfig {
        minSdkVersion(App.minSdk)
        targetSdkVersion(App.targetSdk)
    }
}

dependencies {
    implementation(project(Modules.app))
    implementation(project(Modules.audioBase))

    api(Libs.kotlin)

    implementation(Libs.groupie)
    implementation(Libs.groupieAndroidExtensions)
}