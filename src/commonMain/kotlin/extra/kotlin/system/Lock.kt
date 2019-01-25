package extra.kotlin.system

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