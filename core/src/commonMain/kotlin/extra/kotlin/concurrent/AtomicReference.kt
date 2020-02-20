package extra.kotlin.concurrent

expect class AtomicReference<T>(value_: T) {
    var value: T
    fun compareAndSet(expected: T, new: T): Boolean
}
