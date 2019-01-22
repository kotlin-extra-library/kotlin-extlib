package extra.kotlin.util.concurrent

// TODO native implementation https://discuss.kotlinlang.org/t/replacement-for-synchronized/11240/3
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