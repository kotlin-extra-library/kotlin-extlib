import extra.kotlin.build.`TRAVIS-TAG`

subprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

allprojects {
    group = "org.kotlinextra"
    version = `TRAVIS-TAG` ?: "0.1.6"
}
