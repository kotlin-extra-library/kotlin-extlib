package extra.kotlin.concurrent

expect class AtomicInt(value_: Int) {
    var value: Int
    fun addAndGet(delta: Int): Int
    fun compareAndSet(expected: Int, new: Int): Boolean
    fun increment()
    fun decrement()
}
