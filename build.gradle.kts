//apply(from = "dependencies/dependencies.gradle.kts")
buildscript {
    //val kotlin_version by extra("1.8.20")
    val kotlin_version by extra("1.6.10")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        //classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    }
}

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    //id("com.google.dagger.hilt.android") version "2.44" apply false
    //id("org.jetbrains.kotlin.jvm") version "1.8.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
