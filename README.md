# Kotlin extra library [![](https://jitpack.io/v/lamba92/kotlin-extlib.svg)](https://jitpack.io/#lamba92/kotlin-extlib)
This library is aimed to become a community powered extension of the Kotlin common standard library. 

WARNING: At the current status the artifacts available are those for Linux, JVm and JS. As soon as I figure out how publish on Maven-Central multistep I'll publish the ones for MacOS, iOS and Windows.

## Installing  [![](https://jitpack.io/v/lamba92/kotlin-extlib.svg)](https://jitpack.io/#lamba92/kotlin-extlib)

Add the [JitPack.io](http://jitpack.io) repository to the project `build.grade`:
```
repositories {
    maven { url 'https://jitpack.io' }
}
```

Then import the latest version in the `build.gradle` of the modules you need:

```
dependencies {
    implementation 'com.github.lamba92.kotlin-extlib:kotlin-extlib{-backend}:{latest_version}'
}
```
Available backends are:
- `-js`
- `-jvm`

Omit the backend syntax for multiplatform declarations: 
`com.github.lamba92.kotlin-extlib:kotlin-extlib:{latest_version}`


If using Gradle Kotlin DSL:
```
repositories {
    maven(url = "https://jitpack.io")
}
...
dependencies {
    implementation("com.github.lamba92.kotlin-extlib", "kotlin-extlib{-backend}", "{latest_version}")
}