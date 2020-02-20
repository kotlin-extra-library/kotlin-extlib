@file:Suppress("NOTHING_TO_INLINE")

package extra.kotlin.bytes

inline fun Int.bitLow(index: Int): Boolean = ushr(index).and(0x1) == 1
inline fun Int.bitLowOn(index: Int) = this or 0x1.shl(index)
inline fun Int.bitLowOff(index: Int) = and(1.shl(index).inv())
inline fun Int.bitLowFlip(index: Int) = xor(1.shl(index))
inline fun Int.bitLowSet(index: Int, value: Boolean) = if(value) bitLowOn(index) else bitLowOff(index)
inline fun Int.bitsLow(): BooleanIterator = object : BooleanIterator() {
    var index = 0
    override fun hasNext(): Boolean = index < 32
    override fun nextBoolean(): Boolean = bitLow(index++)
}

inline fun Int.bitHigh(index: Int): Boolean = ushr(31-index).and(0x1) == 1
inline fun Int.bitHighOn(index: Int) = this or 0x1.shl(31-index)
inline fun Int.bitHighOff(index: Int) = and(1.shl(31-index).inv())
inline fun Int.bitHighFlip(index: Int) = xor(1.shl(31-index))
inline fun Int.bitHighSet(index: Int, value: Boolean) = if(value) bitHighOn(index) else bitHighOff(index)
inline fun Int.bitsHigh(): BooleanIterator = object : BooleanIterator() {
    var index = 0
    override fun hasNext(): Boolean = index < 32
    override fun nextBoolean(): Boolean = bitHigh(index++)
}

inline fun Long.bitLow(index: Int): Boolean = ushr(index).and(0x1) == 1L
inline fun Long.bitLowOn(index: Int) = this or 0x1L.shl(index)
inline fun Long.bitLowOff(index: Int) = and(1L.shl(index).inv())
inline fun Long.bitLowFlip(index: Int) = xor(1L.shl(index))
inline fun Long.bitLowSet(index: Int, value: Boolean) = if(value) bitLowOn(index) else bitLowOff(index)
inline fun Long.bitsLow(): BooleanIterator = object : BooleanIterator() {
    var index = 0
    override fun hasNext(): Boolean = index < 64
    override fun nextBoolean(): Boolean = bitLow(index++)
}

inline fun Long.bitHigh(index: Int): Boolean = ushr(63-index).and(0x1) == 1L
inline fun Long.bitHighOn(index: Int) = this or 0x1L.shl(63-index)
inline fun Long.bitHighOff(index: Int) = and(1L.shl(63-index).inv())
inline fun Long.bitHighFlip(index: Int) = xor(1L.shl(63-index))
inline fun Long.bitHighSet(index: Int, value: Boolean) = if(value) bitHighOn(index) else bitHighOff(index)
inline fun Long.bitsHigh(): BooleanIterator = object : BooleanIterator() {
    var index = 0
    override fun hasNext(): Boolean = index < 64
    override fun nextBoolean(): Boolean = bitHigh(index++)
}
