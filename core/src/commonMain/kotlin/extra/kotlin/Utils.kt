package extra.kotlin

import kotlin.js.JsName
import kotlin.jvm.JvmName

@Deprecated("Package moved", ReplaceWith("this.getBytesLE()", "extra.kotlin.bytes.getBytesLE"))
fun Long.getBytesLE() = ByteArray(8).apply {
    var buff = this@getBytesLE
    repeat(size) {
        this[it] = buff.toByte()
        buff = buff shr 8
    }
}

@Deprecated("Package moved", ReplaceWith("this.getBytesLE()", "extra.kotlin.bytes.getBytesLE"))
fun Double.getBytesLE()
        = toRawBits().getBytesLE()

@Deprecated("Package moved", ReplaceWith("this.upperNibble", "extra.kotlin.bytes.upperNibble"))
val Byte.upperNibble get() = (this.toInt() shr 4 and 0b1111).toByte()

@Deprecated("Package moved", ReplaceWith("this.lowerNibble", "extra.kotlin.bytes.lowerNibble"))
val Byte.lowerNibble get() = (this.toInt() and 0b1111).toByte()

@Deprecated("Package moved", ReplaceWith("this.writeBytesLE()", "extra.kotlin.bytes.writeBytesLE"))
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

