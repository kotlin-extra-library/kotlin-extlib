package extra.kotlin.exception

import java.io.PrintWriter
import java.io.StringWriter

actual fun Throwable.stackTraceString(): String {
    val underlying = StringWriter()
    val writer = PrintWriter(underlying)
    writer.println("${javaClass.simpleName}: $message")
    printStackTrace(writer)
    return underlying.toString()
}
