pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        //id("com.guardsquare.proguard") version "7.1.0"
        //classpath 'com.guardsquare:proguard-gradle:7.1.0'
    }
}

rootProject.name = "DeskTopApp"

