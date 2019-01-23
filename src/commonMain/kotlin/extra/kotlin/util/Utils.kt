package extra.kotlin.util

import kotlin.experimental.and

//@ExperimentalUnsignedTypes
//fun Byte.toHexChars(): String {
//    val msb = (this ushr 4).toInt()
//    val lsb = (this and 15).toInt()
//
//    return buildString {
//        when {
//            msb <= 9 -> append(msb)
//            else -> append((msb+87).toChar())
//        }
//        when {
//            lsb <= 9 -> append(msb)
//            else -> append((lsb+87).toChar())
//        }
//    }
//}
//
//@ExperimentalUnsignedTypes
//infix fun UByte.shr(i: Int)
//    = (this.toUInt() shr i).toUByte()
//
//
//infix fun Byte.ushr(i: Int)
//    = (this.toInt() ushr i).toByte()
//
//@ExperimentalUnsignedTypes
//fun Long.getBytes() = ByteArray(8).apply {
//    var buff = this@getBytes
//    repeat(size){
//        this[it] = buff.toByte()
//        buff = buff shl 8
//    }
//}

infix fun Byte.and(i: Int) = toInt() and i

