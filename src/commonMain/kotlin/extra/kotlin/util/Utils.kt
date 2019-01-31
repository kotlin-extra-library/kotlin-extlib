package extra.kotlin.util

fun Long.getBytes() = ByteArray(8).apply {
    var buff = this@getBytes
    repeat(size) {
        this[it] = buff.toByte()
        buff = buff shr 8
    }
}

fun Double.getBytes()
        = toRawBits().getBytes()

val Byte.upperNibble get() = (this.toInt() shr 4 and 0b1111).toByte()
val Byte.lowerNibble get() = (this.toInt() and 0b1111).toByte()
@ExperimentalUnsignedTypes
val UByte.upperNibble get() = (this.toInt() shr 4 and 0b1111).toUByte()
@ExperimentalUnsignedTypes
val UByte.lowerNibble get() = (this.toInt() and 0b1111).toUByte()


