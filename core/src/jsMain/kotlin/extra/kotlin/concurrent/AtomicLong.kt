package extra.kotlin.concurrent


actual class AtomicLong actual constructor(value_: Long) {

    actual var value: Long = value_

    actual fun compareAndSet(expected: Long, new: Long): Boolean {
        if (expected == value) {
            value = new
            return true
        }
        return false
    }

    actual fun addAndGet(delta: Long): Long {
        value += delta
        return value
    }
    actual fun increment() { addAndGet(1) }
    actual fun decrement() { addAndGet(-1) }
}
