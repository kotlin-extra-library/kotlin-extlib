package extra.kotlin.native.reroute

import kotlin.native.concurrent.ensureNeverFrozen
import kotlin.native.concurrent.freeze
import kotlin.native.concurrent.isFrozen

fun Any.rerouteFrozen(): Boolean = this.isFrozen
fun <T> T.rerouteFreeze(): T = this.freeze()
fun Any.rerouteEnsureNeverFrozen(): Unit = this.ensureNeverFrozen()
