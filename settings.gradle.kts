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
        }
    }
}
rootProject.name = "kotlin-extlib"
enableFeaturePreview("GRADLE_METADATA")
