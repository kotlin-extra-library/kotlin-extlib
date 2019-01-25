package extra.kotlin.collection

import kotlin.coroutines.AbstractCoroutineContextElement

fun <E> Collection<E>.addTo(collection: MutableCollection<E>) = collection.addAll(this)