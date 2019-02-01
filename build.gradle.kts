import groovy.util.Node
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
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
version = "0.0.3"

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
    metadata {
        mavenPublication {
            artifactId = "kotlin-extlib-common"
        }
    }

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
            tasks.withType<AbstractPublishToMaven>().all {
                onlyIf {
                    publication != this@mavenPublication || OperatingSystem.current().isLinux
                }
            }
        }
    }

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

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.value("javadoc")
    // TODO: instead of a single empty Javadoc JAR, generate real documentation for each module
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.value("sources")
}

publishing {
    publications {
        configure(withType<MavenPublication>()) {
            signing.sign(this)
            customizeForMavenCentral(pom)
            artifact(javadocJar)
        }
        withType<MavenPublication>()["kotlinMultiplatform"].artifact(sourcesJar)
    }
    repositories {
        maven(url = "https://oss.sonatype.org/service/local/staging/deploy/maven2")
            .credentials {
                val sonatypeUsername: String by project
                val sonatypePassword: String by project
                username = sonatypeUsername
                password = sonatypePassword
            }
    }
}

fun customizeForMavenCentral(pom: org.gradle.api.publish.maven.MavenPom) = pom.buildAsNode {
    add("description", "Kotlin community common multiplatform library")
    add("name", "Kotlin Extra Library")
    add("url", "https://github.com/lamba92/kotlin-extlib")
    node("organization") {
        add("name", "com.github.lamba92")
        add("url", "https://github.com/lamba92")
    }
    node("issueManagement") {
        add("system", "github")
        add("url", "https://github.com/lamba92/kotlin-extlib/issues")
    }
    node("licenses") {
        node("license") {
            add("name", "Apache License 2.0")
            add("url", "https://github.com/lamba92/kotlin-extlib/blob/master/LICENSE")
            add("distribution", "repo")
        }
    }
    node("scm") {
        add("url", "https://github.com/lamba92/kotlin-extlib")
        add("connection", "scm:git:git://github.com/lamba92/kotlin-extlib.git")
        add("developerConnection", "scm:git:ssh://github.com/lamba92/kotlin-extlib.git")
    }
    node("developers") {
        node("developer") {
            add("name", "Lamba92")
        }
    }
}

fun KotlinNativeTarget.compilations(name: String, config: KotlinNativeCompilation.() -> Unit) =
    compilations[name].apply(config)


fun properties(file: File) = Properties().apply { load(file.inputStream()) }
fun properties(fileSrc: String) = properties(file(fileSrc))

val KotlinMultiplatformExtension.nativeTargets
    get() = targets.filter { it is KotlinNativeTarget }.map { it as KotlinNativeTarget }

val KotlinMultiplatformExtension.platformIndependentTargets
    get() = targets.filter { it !is KotlinNativeTarget || it.konanTarget == KonanTarget.WASM32 }

val KotlinMultiplatformExtension.appleTargets
    get() = targets.filter {
        it is KotlinNativeTarget && listOf(
            KonanTarget.IOS_ARM64,
            KonanTarget.IOS_X64,
            KonanTarget.MACOS_X64
        ).any { target -> it == target }
    }

fun Node.add(key: String, value: String) = appendNode(key).setValue(value)

fun Node.node(key: String, content: Node.() -> Unit) = appendNode(key).also(content)

fun org.gradle.api.publish.maven.MavenPom.buildAsNode(builder: Node.() -> Unit) = withXml { asNode().apply(builder) }
