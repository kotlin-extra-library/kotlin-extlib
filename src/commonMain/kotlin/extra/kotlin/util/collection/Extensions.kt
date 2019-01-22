package extra.kotlin.util.collection

fun <E> Collection<E>.addTo(collection: MutableCollection<E>) = collection.addAll(this)