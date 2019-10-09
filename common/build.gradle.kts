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
}

dependencies {
    api(Libs.kotlin)
    api(Libs.coroutinesCore)
    api(Libs.coroutinesAndroid)

    /* DFM */
    api(Libs.playCore)

    api(Libs.navigationFragment)
    api(Libs.navigationUi)
    api(Libs.appcompat)
    api(Libs.coreKtx)
    api(Libs.constraintlayout)
    api(Libs.lifecycleExtensions)
    api(Libs.lifecycleViewModel)
}
