package extra.kotlin.system

import kotlin.js.Date

actual fun getCurrentTimeInMillis() = Date().getTime().toLong()