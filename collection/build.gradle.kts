plugins {
    id("kotlin-extlib-gradle-plugin")
}

kotlin.sourceSets {
    val commonMain by getting {
        dependencies {
            api(project(":core"))
        }
    }
}
