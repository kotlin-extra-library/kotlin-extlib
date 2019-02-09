package test.extra.kotlin

import extra.kotlin.getBytesLE
import extra.kotlin.lowerNibble
import extra.kotlin.upperNibble
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UtilsTest {
    @Test
    fun `test Long getBytesLE`() {
        assertTrue(ByteArray(8) { 0 }.contentEquals((0L).getBytesLE()))
        assertTrue(ByteArray(8) { 0xFF.toByte() }.contentEquals((-1L).getBytesLE()))
        assertTrue(
            byteArrayOf(
                0x01, 0x23, 0x45, 0x67,
                0x89.toByte(), 0xab.toByte(), 0xcd.toByte(), 0xef.toByte()
            ).contentEquals((Long.MIN_VALUE + 0x6fcdab8967452301L).getBytesLE())
        )
    }

    // TODO test Double.getBytesLE()

    @Test
    fun `test Byte upperNibble`(){
        assertEquals(0x00, 0x00.toByte().upperNibble)
        assertEquals(0x00, 0x0F.toByte().upperNibble)
        assertEquals(0x0F, 0xF0.toByte().upperNibble)
        assertEquals(0x0F, 0xFF.toByte().upperNibble)
    }

    @ExperimentalUnsignedTypes
    @Test
    fun `test Byte lowerNibble`(){
        assertEquals(0x00.toUByte(), 0x00.toUByte().lowerNibble)
        assertEquals(0x0F.toUByte(), 0x0F.toUByte().lowerNibble)
        assertEquals(0x00.toUByte(), 0xF0.toUByte().lowerNibble)
        assertEquals(0x0F.toUByte(), 0xFF.toUByte().lowerNibble)
    }
}
