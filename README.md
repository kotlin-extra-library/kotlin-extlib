# Kotlin extra library [![Maven Central](https://img.shields.io/maven-central/v/com.github.lamba92/kotlin-extlib-common.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.lamba92%22%20AND%20a:%22kotlin-extlib-common%22)
This library is aimed to become a community powered extension of the Kotlin common standard library. 

No OSX available until I get my hands on a macbook

## Installing  [![Maven Central](https://img.shields.io/maven-central/v/com.github.lamba92/kotlin-extlib-common.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.lamba92%22%20AND%20a:%22kotlin-extlib-common%22)

Import the latest version in the `build.gradle` of the modules you need:

```
dependencies {
    implementation 'com.github.lamba92:kotlin-extlib-{backend}:{latest_version}'
}
```

If using Gradle Kotlin DSL:
```
dependencies {
    implementation("com.github.lamba92", "kotlin-extlib{-backend}", "{latest_version}")
}
```

Available backends are:
- `common`
- `js`
- `jvm`
- `linuxarm32hfp`
- `linuxmips32`
- `linuxmipsel32`
- `linuxx64`
- `androidnativearm64`
- `mingwx64`

