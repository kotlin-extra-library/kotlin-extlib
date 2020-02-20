package extra.kotlin.collection

import kotlin.math.abs

/**
 * A Sorted [Set] implemented using a sorted [ArrayList]. The insertion is ordered using [binarySearch]
 */
class SortedSet<E>(
    val comparator: Comparator<E>,
    collection: Collection<E> = emptyList()
) : AbstractMutableSet<E>() {

    private val data = ArrayList<E>().apply {
        addAll(collection.toSet())
        sortWith(comparator)
    }

    override val size: Int
            get() = data.size

    override fun add(element: E): Boolean {
        val index = data.binarySearch(element, comparator)
        return if (index >= 0)
            false
        else {
            data.add(abs(index) - 1, element)
            true
        }
    }

    override fun iterator() = data.iterator()
}