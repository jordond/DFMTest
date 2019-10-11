import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(RootPlugins.androidGradle)
        classpath(RootPlugins.kotlinGradle)
        classpath(RootPlugins.navigationSafeArgs)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}