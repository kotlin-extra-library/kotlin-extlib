package extra.kotlin.native

import extra.kotlin.native.reroute.rerouteEnsureNeverFrozen
import extra.kotlin.native.reroute.rerouteFreeze
import extra.kotlin.native.reroute.rerouteFrozen

actual fun <T: Any> T.freeze(): T = rerouteFreeze()
actual fun Any.ensureNeverFrozen() = rerouteEnsureNeverFrozen()
actual val Any.isFrozen: Boolean get() = rerouteFrozen()
