import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.Properties

plugins {
    kotlin("multiplatform") version "1.3.20"
    id("maven-publish")
    id("signing")
}

repositories {
    mavenCentral()
    jcenter()
}

group = "it.lamba"
version = "0.0.1"

kotlin {

    sourceSets.create("nativeCommon")
    sourceSets.create("jvmCommon")

    jvm {
        compilations["main"].kotlinOptions.jvmTarget = "1.8"
    }
    js()
    wasm32 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }

    iosArm64 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    mingwX64 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    macosX64 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    linuxX64 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    linuxArm32Hfp {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    linuxMips32 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    linuxMipsel32 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }
    androidNativeArm64 {
        compilations("main") {
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }

    configure(listOf(jvm(), js(), metadata(), wasm32())) {
        mavenPublication {
            val linuxOnlyPublication = this@mavenPublication
            tasks.withType<AbstractPublishToMaven>().all {
                onlyIf {
                    publication != linuxOnlyPublication || OperatingSystem.current().isLinux
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:atomicfu-common:0.12.1")
//                implementation("com.github.lamba92.kotlin-extlib:kotlin-extlib:0.0.5")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependsOn(sourceSets["jvmCommon"])
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

        val iosArm64Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
        val mingwX64Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
        val macosX64Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
        val linuxX64Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }

        val linuxArm32HfpMain by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
        val linuxMips32Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
        val linuxMipsel32Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
        val androidNativeArm64Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
        val wasm32Main by getting {
            dependsOn(sourceSets["nativeCommon"])
        }
    }
}

signin {
    // TODO
}

publishing {
    repositories {
        maven(url = "https://oss.sonatype.org/service/local/staging/deploy/maven2")
            .credentials {
                val sonatypeUsername: String by properties("local.properties")
                val sonatypePassword: String by properties("local.properties")
                username = sonatypeUsername
                password = sonatypePassword
            }
    }
}

fun KotlinNativeTarget.compilations(name: String, config: KotlinNativeCompilation.() -> Unit) =
    compilations[name].apply(config)

fun org.gradle.api.Project.signin(config: SigningExtension.() -> Unit) = configure(config)

fun properties(file: File) = Properties().apply{ load(file.inputStream()) }
fun properties(fileSrc: String) = properties(file(fileSrc))