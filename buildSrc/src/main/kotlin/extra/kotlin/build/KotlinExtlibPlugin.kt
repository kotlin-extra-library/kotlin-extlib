package extra.kotlin.build

import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.jfrog.bintray.gradle.BintrayPlugin
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.gradle.api.*
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Sync
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinPackageJsonTask

@Suppress("UNUSED_VARIABLE")
class KotlinExtlibPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {

        apply<MavenPublishPlugin>()
        apply<AndroidLibraryPlugin>()
        apply<KotlinMultiplatformPluginWrapper>()
        apply<BintrayPlugin>()
        apply<SigningPlugin>()

        android {

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            compileSdkVersion(29)
            buildToolsVersion("30.0.0-rc1")

            defaultConfig {
                minSdkVersion(14)
            }

            alignSourcesForKotlinMultiplatformPlugin(target)
        }

        val publicationLambda = Action<MavenPublication> {
            if (!artifactId.startsWith(rootProject.name))
                artifactId = "${rootProject.name}-$artifactId"
        }

        kotlin {
            android {
                publishLibraryVariants("release")
                mavenPublication(publicationLambda)
                compilations.all {
                    kotlinOptions.jvmTarget = "1.8"
                }
            }

            js {
                browser()
                nodejs()
                mavenPublication {
                    val jsPackageJson: KotlinPackageJsonTask by tasks
                    artifact(jsPackageJson.packageJson)
                }
            }

            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = "1.8"
                }
            }

            val mainTarget = mingwX64()
            val otherTargets = listOf(
                mingwX86(),
                iosArm32(),
                iosArm64(),
                iosX64(),
                macosX64(),
                watchosArm32(),
                watchosArm64(),
                watchosX86(),
                tvosArm64(),
                androidNativeArm32(),
                androidNativeArm64(),
                androidNativeX64(),
                androidNativeX86(),
                linuxArm32Hfp(),
                linuxArm64(),
                linuxMips32(),
                linuxMipsel32(),
                wasm32()
            )

            val syncNativeTargets = task<Sync>("sync${mainTarget.name.capitalize()}Sources") {
                group = "native source copy"
                from(mainTarget.compilations["main"].defaultSourceSet.kotlin.sourceDirectories)
                into("$buildDir/generated/nativeCopy")
            }

            val syncJvmTargets = task<Sync>("sync${jvm().name.capitalize()}Sources") {
                group = "native source copy"
                from(jvm().compilations["main"].defaultSourceSet.kotlin.sourceDirectories)
                into("$buildDir/generated/jvmCopy")
            }

            configure(otherTargets) {
                compilations["main"].apply {
                    defaultSourceSet.kotlin.srcDir(syncNativeTargets.destinationDir)
                    compileKotlinTask.dependsOn(syncNativeTargets)
                }
            }

            android.sourceSets["main"].java.srcDir(syncJvmTargets.destinationDir)

            tasks.all {
                if ("android" in name.toLowerCase() && "kotlin" in name.toLowerCase())
                    dependsOn(syncJvmTargets)
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
                val androidMain by getting {
                    dependencies {
                        implementation(kotlin("stdlib-jdk8"))
                    }
                }
                val androidTest by getting {
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

        publishing.publications.withType(publicationLambda.asLambda())

        tasks.withType<BintrayUploadTask> {
            doFirst {
                publishing.publications.withType<MavenPublication> {
                    buildDir.resolve("publications/$name/module.json").let {
                        if (it.exists())
                            artifact(object : FileBasedMavenArtifact(it) {
                                override fun getDefaultExtension() = "module"
                            })
                    }
                }
            }
        }

        bintray {
            user = searchPropertyOrNull("bintrayUsername", "BINTRAY_USERNAME")
            key = searchPropertyOrNull("bintrayApiKey", "BINTRAY_API_KEY")
            pkg {
                version {
                    name = project.version as String
                }
                repo = "org.extra.kotlin"
                name = "kotlin-extlib"
                description = "Kotlin community common multiplatform library"
                license = listOf("Apache-2.0")
                vcsUrl = "https://github.com/kotlin-extra-library/kotlin-extlib"
                issueTrackerUrl = "https://github.com/kotlin-extra-library/kotlin-extlib/issues"
            }
            publish = true
            setPublicationsOsBased(
                kotlin.appleTargets.names,
                kotlin.windowsTargets.names,
                (publishing.publications.names - kotlin.appleTargets.names - kotlin.windowsTargets.names
                        + "androidRelease")
            )
        }

    }
}
