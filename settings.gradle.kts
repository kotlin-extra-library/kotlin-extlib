pluginManagement {
    repositories {
        mavenCentral()
        maven(url="https://plugins.gradle.org/m2/")
        google()
    }

    resolutionStrategy {
        eachPlugin {
            when(requested.id.id){
                "kotlin-multiplatform"-> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "com.android.library" -> useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
}
rootProject.name = "kotlin-extlib"
enableFeaturePreview("GRADLE_METADATA")
