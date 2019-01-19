pluginManagement {
    repositories {
        maven(url="https://dl.bintray.com/kotlin/kotlin-eap")
        mavenCentral()
        maven(url="https://plugins.gradle.org/m2/")
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }

            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
        }
    }
}
rootProject.name = "kotlin-extra-library"


enableFeaturePreview("GRADLE_METADATA")
rootProject.name = "kotlin-common-library"


enableFeaturePreview("GRADLE_METADATA")
