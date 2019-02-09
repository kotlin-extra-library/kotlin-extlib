@file:Suppress("NOTHING_TO_INLINE")

package extra.kotlin

import org.khronos.webgl.Int8Array

@Suppress("CAST_NEVER_SUCCEEDS")
inline fun Int8Array.asByteArray(): ByteArray = this as ByteArray

@Suppress("CAST_NEVER_SUCCEEDS")
inline fun ByteArray.asInt8Array(): Int8Array = this as Int8Array
