plugins {
    id(BuildPlugins.dynamicFeature)
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
}
