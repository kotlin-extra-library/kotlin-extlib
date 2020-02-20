import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("kotlin-extlib-gradle-plugin")
}

kotlin.targets.withType<KotlinNativeTarget>() {
    compilations.all {
        cinterops {
            create("mutex") {
                defFile = file("src/nativeInterop/cinterop/nativeMutex.def")
            }
        }
    }
}
