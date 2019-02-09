package extra.kotlin.concurrent


actual class AtomicReference<T> actual constructor(value_: T) {
    val backing = java.util.concurrent.atomic.AtomicReference<T>(value_)

    actual var value: T
        get() = backing.get()
        set(value) {
            backing.set(value)
        }

    actual fun compareAndSet(expected: T, new: T): Boolean = backing.compareAndSet(expected, new)
}
