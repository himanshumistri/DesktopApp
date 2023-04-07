
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

var ktorVersion ="2.2.3"
val slf4j ="2.0.7"
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
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-server-websockets:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-websockets:$ktorVersion")
                implementation("io.ktor:ktor-client-jetty:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
                implementation("org.slf4j:slf4j-api:$slf4j")
                // https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12
                //slf4j-log4j12
                //testImplementation("org.slf4j:slf4j-log4j12:1.2")
                implementation("org.slf4j:slf4j-log4j12:$slf4j")
                //implementation("io.ktor:ktor-server-jetty:$ktorVersion")
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
            buildTypes.release.proguard {
                obfuscate.set(true)
                configurationFiles.from(project.file("proguard-rules.pro"))
            }
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