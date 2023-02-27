
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.himotech.desktop"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
               // implementation("com.guardsquare:proguard-gradle:7.2.1")
            }
        }
        val jvmTest by getting
    }
}
//val obfuscate by tasks.registering(proguard.gradle.ProGuardTask::class)

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "DeskTopApp"
            packageVersion = "1.0.0"
        }
    }
}

/*obfuscate.configure {
    dependsOn(tasks.jar.get())

    val allJars = tasks.jar.get().outputs.files + sourceSets.main.get().runtimeClasspath.filter { it.path.endsWith(".jar") }
        .filterNot { it.name.startsWith("skiko-awt-") && !it.name.startsWith("skiko-awt-runtime-") } // walkaround https://github.com/JetBrains/compose-jb/issues/1971

    for (file in allJars) {
        injars(file)
        outjars(mapObfuscatedJarFile(file))
    }

    libraryjars("${compose.desktop.application.javaHome ?: System.getProperty("java.home")}/jmods")

    configuration("proguard-rules.pro")
}*/