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
}
