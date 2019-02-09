package extra.kotlin.exception

/**
 * Returns the stack trace of the throwable as a string as best it can
 */
expect fun Throwable.stackTraceString(): String
