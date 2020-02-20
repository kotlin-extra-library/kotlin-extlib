package extra.kotlin.exception

/**
 * Returns the stack trace of the throwable as a string as best it can
 */
actual fun Throwable.stackTraceString(): String {
    return generateSequence<Throwable>(this) { it.cause }.joinToString("\n") {
        it.toString() + ": " + it.message
    }
}
