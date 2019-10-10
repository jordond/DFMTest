import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(BuildPlugins.androidGradle)
        classpath(BuildPlugins.kotlinGradle)
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