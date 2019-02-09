package extra.kotlin.bytes

import kotlin.js.JsName
import kotlin.jvm.JvmName

fun Long.getBytesLE() = ByteArray(8).apply {
    var buff = this@getBytesLE
    repeat(size) {
        this[it] = buff.toByte()
        buff = buff shr 8
    }
}

fun Double.getBytesLE()
        = toRawBits().getBytesLE()

val Byte.upperNibble get() = (this.toInt() shr 4 and 0b1111).toByte()
val Byte.lowerNibble get() = (this.toInt() and 0b1111).toByte()
@ExperimentalUnsignedTypes
@get:JvmName("upperNibbleUnsigned")
@get:JsName("upperNibbleUnsigned")
val UByte.upperNibble get() = (this.toInt() shr 4 and 0b1111).toUByte()
@ExperimentalUnsignedTypes
@get:JvmName("lowerNibbleUnsigned")
@get:JsName("lowerNibbleUnsigned")
val UByte.lowerNibble get() = (this.toInt() and 0b1111).toUByte()

fun ByteArray.writeBytesLE(value: Long, offset : Int = 0) : Int {
    if (this.size - offset >= 8)
        throw IndexOutOfBoundsException("The remaining space is less then 8")
    var buff = value

    repeat(8){
        this[it+offset] = buff.toByte()
        buff = buff shr 8
    }

    return offset + 8
}

