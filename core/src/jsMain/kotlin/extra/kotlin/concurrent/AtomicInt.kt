package extra.kotlin.concurrent


actual class AtomicInt actual constructor(value_: Int) {

    actual var value: Int = value_

    actual fun compareAndSet(expected: Int, new: Int): Boolean {
        if (expected == value) {
            value = new
            return true
        }
        return false
    }

    actual fun addAndGet(delta: Int): Int {
        value += delta
        return value
    }

    actual fun increment() { addAndGet(1) }
    actual fun decrement() { addAndGet(-1) }
}
