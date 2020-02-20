package extra.kotlin.exception

actual fun Throwable.stackTraceString(): String {
    return this.getStackTrace().joinToString("\n")
}
