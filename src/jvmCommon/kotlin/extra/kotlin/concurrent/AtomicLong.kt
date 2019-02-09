package extra.kotlin.concurrent


actual class AtomicLong actual constructor(value_: Long) {
    val backing = java.util.concurrent.atomic.AtomicLong(value_)

    actual var value: Long
        get() = backing.get()
        set(value) {
            backing.set(value)
        }

    actual fun compareAndSet(expected: Long, new: Long): Boolean = backing.compareAndSet(expected, new)
    actual fun addAndGet(delta: Long): Long = backing.addAndGet(delta)
    actual fun increment() { backing.incrementAndGet() }
    actual fun decrement() { backing.decrementAndGet() }
}
