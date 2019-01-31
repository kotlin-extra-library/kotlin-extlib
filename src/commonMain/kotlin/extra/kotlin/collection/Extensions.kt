package extra.kotlin.collection

/**
 * Adds [this] collection to the [target] collection.
 */
fun <E> Collection<E>.addTo(target: MutableCollection<E>) = target.addAll(this)