# Kotlin extra library [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/kotlin-extlib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/kotlin-extlib)
This library is aimed to become a community powered extension of the Kotlin common standard library. 

No OSX available until I get my hands on a macbook

## Installing  [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/kotlin-extlib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/kotlin-extlib)

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

