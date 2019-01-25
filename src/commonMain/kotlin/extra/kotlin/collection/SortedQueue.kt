package extra.kotlin.collection

class SortedQueue<E>(
    private val comparator: Comparator<E>,
    collection: Collection<E> = emptyList()
) : ArrayListQueue<E>(collection) {

    override fun add(element: E)
            = data.add(element).also { data.sortWith(comparator) }
}