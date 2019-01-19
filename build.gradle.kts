plugins {
    id("kotlin-multiplatform") version "1.3.20-eap-100"
    id("maven-publish")
}
repositories {
    maven(url="https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
}
group = "extra.kotlin"
version = "0.0.1"

kotlin {
    jvm(){
        compilations["main"].kotlinOptions.jvmTarget = "1.8"
    }
    js()

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
