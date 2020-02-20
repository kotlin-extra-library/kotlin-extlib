package extra.kotlin.concurrent

expect class AtomicLong(value_: Long) {
    var value: Long
    fun addAndGet(delta: Long): Long
    fun compareAndSet(expected: Long, new: Long): Boolean
    fun increment()
    fun decrement()
}
