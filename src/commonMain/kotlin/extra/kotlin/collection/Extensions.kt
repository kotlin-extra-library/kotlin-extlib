package extra.kotlin.collection

/**
 * Adds [this] collection to the [target] collection.
 */
fun <E> Collection<E>.addTo(target: MutableCollection<E>) = target.addAll(this)

/**
 * Performs the given [action] on each element.
 */
inline fun <T> Array<out T>.forEachWhile(check: () -> Boolean, action: (T) -> Unit) {
    for (element in this) {
        if(check())
            action(element)
        else
            break
    }
}

inline fun <T> Array<out T>.forEachUntil(check: () -> Boolean, action: (T) -> Unit)
        = forEachWhile(check.negate(), action)

@Suppress("NOTHING_TO_INLINE")
inline fun (() -> Boolean).negate() = {!this()}