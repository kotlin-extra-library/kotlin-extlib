package extra.kotlin.concurrent

//TODO: This would better belong in 'extra.kotlin.concurrent'

expect class Lock() {
    fun lock()
    fun unlock()
}

inline fun <R> Lock.use(block: () -> R): R {
    try {
        lock()
        return block()
    } finally {
        unlock()
    }
}
