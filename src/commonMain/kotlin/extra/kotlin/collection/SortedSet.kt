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
            val i = abs(index)
            data.add(abs(index) - 1, element)
            true
        }
    }

    override fun iterator() = data.iterator()
}

fun main() {
    val s = SortedSet<Int>(Comparator { a, b -> b-a })
    (0..10).forEach { s.add(it) }
    println(buildString {
        s.forEach { append("$it ") }
    })
    println(s.size)
    for(i in 0..12){
        s.add(i)
    }
    println(buildString {
        s.forEach { append("$it ") }
    })
    println(s.size)

}