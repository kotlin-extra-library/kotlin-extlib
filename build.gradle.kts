import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("kotlin-multiplatform") version "1.3.20-eap-100"
    id("maven-publish")
}
repositories {
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
}
group = "extra.kotlin"
version = "0.0.1"

kotlin {
    
    jvm {
        compilations["main"].kotlinOptions.jvmTarget = "1.8"
    }
    js()
    iosArm64 {
        compilations("main"){
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    mingwX64 {
        compilations("main"){
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    macosX64 {
        compilations("main"){
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    linuxX64 {
        compilations("main"){
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
//    linuxArm32Hfp {
//        compilations("main"){
//            outputKinds(DYNAMIC)
//            cinterops.create("nativeMutex") {
//                includeDirs(buildDir)
//            }
//        }
//    }
//    linuxMips32 {
//        compilations("main"){
//            outputKinds(DYNAMIC)
//            cinterops.create("nativeMutex") {
//                includeDirs(buildDir)
//            }
//        }
//    }
//    linuxMipsel32 {
//        compilations("main"){
//            outputKinds(DYNAMIC)
//            cinterops.create("nativeMutex") {
//                includeDirs(buildDir)
//            }
//        }
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val mingwX64Main by getting {

        }
        val macosX64Main by getting {

        }
        val linuxX64Main by getting {

        }
        
        
//        val linuxArm32HfpMain by getting {
//
//        }
//        val linuxMips32Main by getting {
//
//        }
//        val linuxMipsel32Main by getting {
//
//        }
    }
}

fun KotlinNativeTarget.compilations(name: String, config: KotlinNativeCompilation.()->Unit) 
    = compilations[name].apply(config)
