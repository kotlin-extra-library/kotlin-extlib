package test.extra.kotlin.bytes

import extra.kotlin.bytes.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ByteTest {

    @Test
    fun testLowBits() {
        //Sometimes people use integers to efficiently store a set of booleans.
        //You can use IntBitArray for this!
        var flags = 0x0
        flags = flags.bitLowSet(0, true) //Index is number of bits from the right-most bit
        flags = flags.bitLowSet(1, false)
        flags = flags.bitLowSet(2, true)

        //Using binary, we can determine that the integer value is 5.  Let's see if it works.
        assertEquals(5, flags)

        //Let's look at an existing set of complicated flags.
        flags = 0x34721823 //In binary: 0000 0000 ... 0011 0100 0111 0010 0001 1000 0010 0011

        //Let's print out each bit - note this will be reversed of the binary representation.
        println(flags.bitsLow().asSequence().joinToString("") { if (it) "1" else "0" })

        //Looks like #5 should be on.
        assertEquals(true, flags.bitLow(5))

        //Looks like #2 should be off.
        assertEquals(false, flags.bitLow(2))

        //Let's turn #2 on.
        flags = flags.bitLowOn(2)
        //The value should be ... 1100 0010 0011
        assertEquals(0x34721827, flags)
    }

    @Test
    fun testHighBits() {
        var bits = 0
        for(index in 0 .. 31) {
            bits = bits.bitHighOn(index)
        }
        assertEquals(-1, bits)
        for(index in 0 .. 31) {
            bits = bits.bitHighOff(index)
        }
        assertEquals(0, bits)
        for(index in 0 .. 31) {
            bits = bits.bitHighSet(index, true)
        }
        assertEquals(-1, bits)
    }

    @Test
    fun testHighBitsLong() {
        var bits = 0L
        for(index in 0 .. 63) {
            bits = bits.bitHighOn(index)
        }
        assertEquals(-1L, bits)
        for(index in 0 .. 63) {
            bits = bits.bitHighOff(index)
        }
        assertEquals(0L, bits)
        for(index in 0 .. 63) {
            bits = bits.bitHighSet(index, true)
        }
        assertEquals(-1L, bits)
    }
}
