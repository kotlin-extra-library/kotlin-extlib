package extra.kotlin.util.concurrent

import kotlinx.cinterop.cValue

actual class Lock {
    private val mutex = cValue<kextra_mutex_t>()

    init {
        kextra_mutex_create(mutex)
    }

    actual fun lock() {
        kextra_mutex_lock(mutex)
    }

    actual fun unlock() {
        kextra_mutex_unlock(mutex)
    }
}