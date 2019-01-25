import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinOnlyTarget
import org.jetbrains.kotlin.konan.target.KonanTarget
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

group = "com.github.lamba92"
version = "0.0.1"

kotlin {

    sourceSets.create("nativeCommon")
    sourceSets.create("jvmCommon")

    jvm {
        compilations["main"].kotlinOptions.jvmTarget = "1.8"
    }
    js()
    wasm32()
    iosArm64()
    iosX64()
    mingwX64()
    macosX64()
    linuxX64()
    linuxArm32Hfp()
    linuxMips32()
    linuxMipsel32()
    androidNativeArm64()

    configure(nativeTargets) {
        compilations("main") {
            defaultSourceSet.dependsOn(sourceSets["nativeCommon"])
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }

    configure(platformIndependentTargets) {
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

val KotlinMultiplatformExtension.nativeTargets
    get() = targets.filter { it is KotlinNativeTarget }.map { it as KotlinNativeTarget }

val KotlinMultiplatformExtension.platformIndependentTargets
    get() = targets.filter { it !is KotlinNativeTarget || it.konanTarget == KonanTarget.WASM32}
