plugins {
    `kotlin-dsl`
}

gradlePlugin.plugins.create("kotlin-extlib-gradle-plugin") {
    id = "kotlin-extlib-gradle-plugin"
    implementationClass = "extra.kotlin.build.KotlinExtlibPlugin"
}

repositories {
    jcenter()
    google()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

dependencies {
    val kotlinVersion: String by project
    val bintrayVersion: String by project
    val androidGradlePlugin: String by project
    api("org.jetbrains.kotlin", "kotlin-gradle-plugin", kotlinVersion)
    api("com.android.tools.build", "gradle", androidGradlePlugin)
    api("com.jfrog.bintray.gradle", "gradle-bintray-plugin", bintrayVersion)
//    api("de.undercouch", "gradle-download-task", "4.0.2")
}
