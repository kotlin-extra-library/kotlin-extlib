package extra.kotlin.build

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.*
import org.gradle.api.publish.PublishingExtension
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.Family.*
import org.jetbrains.kotlin.konan.target.KonanTarget

@Suppress("PropertyName", "ObjectPropertyName")
val `TRAVIS-TAG`
    get() = with(System.getenv("TRAVIS_TAG")) {
        if (isNullOrBlank()) null else this
    }

typealias AndroidLibraryExtension = LibraryExtension
typealias AndroidLibraryPlugin = LibraryPlugin

@Suppress("FunctionName")
fun Project.android(action: AndroidLibraryExtension.() -> Unit) =
    extensions.configure(action)

@Suppress("FunctionName")
fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) =
    extensions.configure(action)

@Suppress("FunctionName")
fun Project.bintray(action: BintrayExtension.() -> Unit) =
    extensions.configure(action)

@Suppress("FunctionName")
fun Project.publishing(action: PublishingExtension.() -> Unit) =
    extensions.configure(action)

val Project.publishing
    get() = extensions.findByType<PublishingExtension>()!!

val Project.kotlin
    get() = extensions.findByType<KotlinMultiplatformExtension>()!!

val Project.android
    get() = extensions.findByType<AndroidLibraryExtension>()!!

fun BintrayExtension.pkg(action: BintrayExtension.PackageConfig.() -> Unit) {
    pkg(closureOf(action))
}

var BintrayExtension.PackageConfig.license: List<String>
    get() = licenses.toList()
    set(value) = setLicenses(*value.toTypedArray())

fun BintrayExtension.PackageConfig.version(action: BintrayExtension.VersionConfig.() -> Unit) {
    version(closureOf(action))
}

fun BintrayExtension.VersionConfig.mavenCentralSync(action: BintrayExtension.MavenCentralSyncConfig.() -> Unit) {
    mavenCentralSync(closureOf(action))
}

fun BintrayExtension.setPublications(names: Iterable<String>) =
    setPublications(*names.toList().toTypedArray())

fun BintrayExtension.setPublicationsOsBased(
    macOsPublications: Iterable<String> = emptyList(),
    windowsPublications: Iterable<String> = emptyList(),
    linuxPublications: Iterable<String> = emptyList()
): Unit = setPublications(
        when {
            OperatingSystem.current().isMacOsX -> macOsPublications
            OperatingSystem.current().isWindows -> windowsPublications
            OperatingSystem.current().isLinux -> linuxPublications
            else -> error("Unknown OS")
        }
    )

fun BintrayExtension.setPublications(builder: () -> Iterable<String>) =
    setPublications(builder())

fun AndroidLibraryExtension.alignSourcesForKotlinMultiplatformPlugin(project: Project) =
    sourceSets.all {
        java.srcDirs(project.file("src/android${name.capitalize()}/kotlin"))
        res.srcDirs(project.file("src/android${name.capitalize()}/res"))
        resources.srcDirs(project.file("src/android${name.capitalize()}/resources"))
        manifest.srcFile(project.file("src/android${name.capitalize()}/AndroidManifest.xml"))
    }

fun Project.searchPropertyOrNull(name: String, vararg aliases: String): String? {

    fun searchEverywhere(name: String): String? =
        findProperty(name) as? String? ?: System.getenv(name)

    searchEverywhere(name)?.let { return it }

    with(aliases.iterator()) {
        while (hasNext()) {
            searchEverywhere(next())?.let { return it }
        }
    }

    return null
}

fun Project.searchProperty(name: String, vararg aliases: String) =
    searchPropertyOrNull(name, *aliases) ?: throw IllegalArgumentException(buildString {
        append("No property found with name $name")
        if (aliases.isNotEmpty())
            append(" or with alias: ${aliases.joinToString(", ")}")
    })

fun <T> Action<T>.asLambda(): T.() -> Unit =
    { this@asLambda.execute(this) }

fun <T> (T.() -> Unit).asAction(): Action<T> =
    Action<T> { this@asAction(this) }

val KotlinMultiplatformExtension.nativeTargets
    get() = targets.filter { it is KotlinNativeTarget }.map { it as KotlinNativeTarget }

val KotlinMultiplatformExtension.platformIndependentTargets
    get() = targets.filter { it !is KotlinNativeTarget || it.konanTarget == KonanTarget.WASM32 }

val Collection<KotlinTarget>.names
    get() = map { it.name }

val KotlinMultiplatformExtension.appleTargets
    get() = targets.filter {
        it is KotlinNativeTarget && it.konanTarget.family.isAppleFamily
    }

val KotlinMultiplatformExtension.windowsTargets
    get() = targets.filter {
        it is KotlinNativeTarget && it.konanTarget.family == MINGW
    }

val KotlinMultiplatformExtension.linuxTargets
    get() = targets.filter {
        it is KotlinNativeTarget && it.konanTarget.family == LINUX
    }

val KotlinMultiplatformExtension.androidTargets
    get() = targets.filter {
        (it is KotlinNativeTarget && it.konanTarget.family == ANDROID)
                || it is KotlinAndroidTarget
    }

val KotlinMultiplatformExtension.jvmTargets
    get() = targets.filterIsInstance<KotlinJvmTarget>()

val KotlinMultiplatformExtension.jsTargets
    get() = targets.filterIsInstance<KotlinJsTarget>()

val isContinuousIntegration
    get() = !System.getenv("CI").isNullOrBlank()

val isAndroidEnabled
    get () = !isContinuousIntegration || (isContinuousIntegration && OperatingSystem.current().isLinux)

fun <T> T.alsoIf(condition: Boolean, function: (T) -> Unit): T =
    if (condition) apply(function) else this
