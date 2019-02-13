# Kotlin extra library 
This library is aimed to become a community powered extension of the Kotlin common standard library. 

## Installing 

Import the latest version in the `build.gradle` of the modules you need:

```
dependencies {
    implementation 'org.kotlinextra:kotlin-extlib-{backend}:{latest_version}'
}
```

If using Gradle Kotlin DSL:
```
dependencies {
    implementation("org.kotlinextra", "kotlin-extlib-{backend}", "{latest_version}")
}
```

Available backends are:
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-common) `common`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-js/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-js) `js`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-jvm/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-jvm) `jvm`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxarm32hfp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxarm32hfp) `linuxarm32hfp`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxmips32/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxmips32) `linuxmips32`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxmipsel32/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxmipsel32) `linuxmipsel32`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxx64/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-linuxx64) `linuxx64`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-androidnativearm64/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-androidnativearm64) `androidnativearm64`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-mingwx64/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-mingwx64) `mingwx64`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-iosarm64/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-iosarm64) `iosarm64`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-iosx64/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-iosx64) `iosx64`
- [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-macosx64/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kotlinextra/kotlin-extlib-macosx64) `macosx64`
