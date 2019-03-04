import Build_gradle.OS.LINUX
import Build_gradle.OS.WINDOWS
import Build_gradle.OS.MAC
import groovy.util.Node
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.util.Properties

plugins {
    kotlin("multiplatform") version "1.3.21"
    id("maven-publish")
    id("signing")
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

group = "org.kotlinextra"
version = System.getenv()["TRAVIS_TAG"] ?: "0.1.5"

val localProp = properties("local.properties")

kotlin {

    sourceSets.create("nativeCommon")

    jvm {
        configure(compilations) {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js()
//    wasm32() <-- keeps giving error
    iosArm64()
//    iosArm32()
    iosX64()
    mingwX64()
//    mingwX86()
    macosX64()
    linuxX64()
    linuxArm32Hfp()
    linuxMips32()
    linuxMipsel32()
    androidNativeArm64()
    metadata()

    configure(nativeTargets) {
        compilations("main") {
            defaultSourceSet.dependsOn(sourceSets["nativeCommon"])
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }

    publish(platformIndependentTargets + androidTargets) onlyOn LINUX
    publish(windowsTargets) onlyOn WINDOWS
    publish(appleTargets) onlyOn MAC

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
    }
}

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.value("javadoc")
    // TODO: instead of a single empty Javadoc JAR, generate real documentation for each module
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.value("sources")
}

var keyId = recoverProperty("signing.keyId")
var gpgPassword = recoverProperty("signing.password")
var gpgFile = recoverProperty("signing.secretKeyRingFile")
    ?: file("./secret.gpg").run { if (exists()) absolutePath else null }
var sonatypeUsername = recoverProperty("sonatypeUsername")
var sonatypePassword = recoverProperty("sonatypePassword")

setupPublishingForMavenCentral(
    "Kotlin community common multiplatform library",
    "Kotlin Extra Library",
    "https://github.com/lamba92/kotlin-extlib",
    "org.kotlinextra",
    "kotlinextra.org",
    "github",
    "https://github.com/kotlin-extra-library/kotlin-extlib/issues",
    listOf(
        License(
            "Apache License 2.0",
            "https://github.com/kotlin-extra-library/kotlin-extlib/blob/master/LICENSE",
            "repo"
        )
    ),
    "https://github.com/kotlin-extra-library/kotlin-extlib",
    "scm:git:https://github.com/kotlin-extra-library/kotlin-extlib.git",
    "scm:git@github.com:kotlin-extra-library/kotlin-extlib.git",
    listOf(
        Developer("Lamba92", "basti.lamberto@gmail.com"),
        Developer("SOFe", "sofe2038@gmail.com"),
        Developer("UnknownJoe796")
    ),
    javadocJar, sourcesJar
)

fun recoverProperty(propertyName: String) = System.getenv()[propertyName.toUpperCase().replace('.', '_')]
    ?: localProp[propertyName] as String?
    ?: extra.getOrNull(propertyName) as String?

fun setupPublishingForMavenCentral(
    description: String,
    name: String,
    url: String,
    organizationName: String,
    organizationUrl: String,
    issueManagementSystem: String,
    issueManagementUrl: String,
    licenses: List<License>,
    scmUrl: String,
    scmConnection: String,
    scmDevConnection: String,
    developers: List<Developer>,
    javadocJar: Jar,
    sourcesJar: Jar,
    keyId_: String? = keyId,
    gpgPassword_: String? = gpgPassword,
    gpgFile_: String? = gpgFile,
    sonatypeUsername_: String? = sonatypeUsername,
    sonatypePassword_: String? = sonatypePassword
) {
    if (listOf(
            keyId_,
            gpgPassword_,
            gpgFile_,
            sonatypeUsername_,
            sonatypePassword_
        ).none { it == null } && file(gpgFile_!!).exists()
    ) {
        val deployUrl = "https://oss.sonatype.org/" + if ((version as String).contains("snapshot", true))
            "content/repositories/snapshots"
        else
            "service/local/staging/deploy/maven2"

        println("Publishing setup detected. Setting up publishing for\n$deployUrl")

        extra["signing.keyId_"] = keyId_
        extra["signing.password"] = gpgPassword_
        extra["signing.secretKeyRingFile"] = gpgFile_

        publishing {
            publications {
                configure(withType<MavenPublication>()) {
                    signing.sign(this)
                    pom.customizeForMavenCentral(
                        description, name, url,
                        organizationName, organizationUrl,
                        issueManagementSystem, issueManagementUrl,
                        licenses, scmUrl, scmConnection,
                        scmDevConnection, developers
                    )
                    artifact(javadocJar)
                }
                withType<MavenPublication>()["kotlinMultiplatform"].artifact(sourcesJar)
            }
            repositories {
                maven(url = deployUrl).credentials {
                    username = sonatypeUsername_
                    password = sonatypePassword_
                }
            }
        }
    } else println(buildString {
        appendln("Not enough information to publish:")
        appendln("keyId: ${if (keyId_ == null) "NOT " else ""}found")
        appendln("gpgPassword: ${if (gpgPassword_ == null) "NOT " else ""}found")
        appendln("gpgFile: ${gpgFile_ ?: "NOT found"}")
        appendln("gpgFile presence: ${gpgFile_?.let { file(it).exists() } ?: "false"}")
        appendln("sonatypeUsername: ${if (sonatypeUsername_ == null) "NOT " else ""}found")
        appendln("sonatypePassword: ${if (sonatypePassword_ == null) "NOT " else ""}found")
    })
}

data class License(val name: String, val url: String, val distribution: String)
data class Developer(val name: String, val email: String? = null)

fun org.gradle.api.publish.maven.MavenPom.customizeForMavenCentral(
    description: String,
    name: String,
    url: String,
    organizationName: String,
    organizationUrl: String,
    issueManagementSystem: String,
    issueManagementUrl: String,
    licenses: List<License>,
    scmUrl: String,
    scmConnection: String,
    scmDevConnection: String,
    developers: List<Developer>
) = buildAsNode {
    add("description", description)
    add("name", name)
    add("url", url)
    node("organization") {
        add("name", organizationName)
        add("url", organizationUrl)
    }
    node("issueManagement") {
        add("system", issueManagementSystem)
        add("url", issueManagementUrl)
    }
    node("licenses") {
        licenses.forEach {
            node("license") {
                add("name", it.name)
                add("url", it.url)
                add("distribution", it.distribution)
            }
        }
    }
    node("scm") {
        add("url", scmUrl)
        add("connection", scmConnection)
        add("developerConnection", scmDevConnection)
    }
    node("developers") {
        developers.forEach {
            node("developer") {
                add("name", it.name)
                if(it.email != null)
                    add("email", it.email)
            }
        }
    }
}

fun KotlinNativeTarget.compilations(name: String, config: KotlinNativeCompilation.() -> Unit) =
    compilations[name].apply(config)

fun properties(file: File) = Properties().apply { load(file.apply { if (!exists()) createNewFile() }.inputStream()) }

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
//            KonanTarget.IOS_ARM32
        ).any { target -> it.konanTarget == target }
    }

val KotlinMultiplatformExtension.windowsTargets
    get() = targets.filter {
        it is KotlinNativeTarget && listOf(
            KonanTarget.MINGW_X64
//            KonanTarget.MINGW_X86
        ).any { target -> it.konanTarget == target }
    }

val KotlinMultiplatformExtension.linuxTargets
    get() = targets.filter {
        it is KotlinNativeTarget && listOf(
            KonanTarget.LINUX_ARM32_HFP,
            KonanTarget.LINUX_MIPS32,
            KonanTarget.LINUX_MIPSEL32,
            KonanTarget.LINUX_X64
        ).any { target -> it.konanTarget == target }
    }

val KotlinMultiplatformExtension.androidTargets
    get() = targets.filter {
        it is KotlinNativeTarget && listOf(
            KonanTarget.ANDROID_ARM32,
            KonanTarget.ANDROID_ARM64
        ).any { target -> it.konanTarget == target }
    }

fun Node.add(key: String, value: String) = appendNode(key).setValue(value)

fun Node.node(key: String, content: Node.() -> Unit) = appendNode(key).also(content)

fun org.gradle.api.publish.maven.MavenPom.buildAsNode(builder: Node.() -> Unit) = withXml { asNode().apply(builder) }

fun ExtraPropertiesExtension.getOrNull(name: String) = if (has(name)) get(name) else null

fun KotlinMultiplatformExtension.publish(targets: Iterable<KotlinTarget>) = targets

infix fun Iterable<KotlinTarget>.onlyOn(os: OS) {
    configure(this) {
        mavenPublication {
            tasks.withType<AbstractPublishToMaven>().all {
                onlyIf {
                    publication != this@mavenPublication || when (os) {
                        LINUX -> OperatingSystem.current().isLinux
                        MAC -> OperatingSystem.current().isMacOsX
                        WINDOWS -> OperatingSystem.current().isWindows
                    }
                }
            }
        }
    }
}

enum class OS {
    LINUX, MAC, WINDOWS
}