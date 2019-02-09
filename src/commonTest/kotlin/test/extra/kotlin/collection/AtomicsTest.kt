package test.extra.kotlin.concurrent

import extra.kotlin.concurrent.AtomicInt
import extra.kotlin.concurrent.AtomicLong
import extra.kotlin.concurrent.AtomicReference
import kotlin.test.*

class AtomicsTest {
    @Test
    fun ref() {
        val firstValue = "asdf"
        val newValue = "asdfg"
        val atomic = AtomicReference(firstValue)
        assertEquals(firstValue, atomic.value)
        assertFalse(atomic.compareAndSet(newValue, newValue))
        assertEquals(firstValue, atomic.value)
        assertTrue(atomic.compareAndSet(firstValue, newValue))
        assertEquals(newValue, atomic.value)
    }

    @Test
    fun integer() {
        val atomic = AtomicInt(3)
        assertEquals<Int>(3, atomic.value)
        assertEquals<Int>(4, atomic.addAndGet(1))
        assertEquals<Int>(4, atomic.value)
        assertFalse(atomic.compareAndSet(88, 5))
        assertEquals<Int>(4, atomic.value)
        assertTrue(atomic.compareAndSet(4, 5))
        assertEquals<Int>(5, atomic.value)
    }

    @Test
    fun long() {
        val atomic = AtomicLong(3L)
        assertEquals<Long>(3L, atomic.value)
        assertEquals<Long>(4L, atomic.addAndGet(1))
        assertEquals<Long>(4L, atomic.value)
        assertFalse(atomic.compareAndSet(88L, 5L))
        assertEquals<Long>(4L, atomic.value)
        assertTrue(atomic.compareAndSet(4L, 5L))
        assertEquals<Long>(5L, atomic.value)
    }
}
