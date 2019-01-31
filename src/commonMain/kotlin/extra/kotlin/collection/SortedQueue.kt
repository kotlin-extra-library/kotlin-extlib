package extra.kotlin.collection

import kotlin.math.abs

/**
 * A simple FIFO sorted queue backed by an [ArrayList]. Insertions are ordered,
 * the index in which make the insertion is searched using [binarySearch]
 *
 * @param comparator The comparator used to sort this queue.
 * @param collection An initial collection from which initialize the queue.
 */
class SortedQueue<E>(
    val comparator: Comparator<E>,
    collection: Collection<E> = emptyList()
) : ArrayListQueue<E>(collection) {

    init {
        if(data.isNotEmpty())
            data.sortWith(comparator)
    }

    override fun add(element: E) =
        data.add(abs(data.binarySearch(element, comparator)) - 1, element).let { true }
}