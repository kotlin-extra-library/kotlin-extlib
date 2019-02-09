package extra.kotlin.collection

/**
 * Creates a recursive sequence, breadth first.
 */
fun <T> Sequence<T>.treeWalkBreadthSequence(getter: (T) -> Sequence<T>) = object : Sequence<T> {
    override fun iterator(): Iterator<T> = TreeWalkBreadthIterator(this@treeWalkBreadthSequence.iterator()) { getter(it).iterator() }
}

/**
 * Creates a recursive sequence, breadth first.
 */
fun <T> Iterable<T>.treeWalkBreadthSequence(getter: (T) -> Sequence<T>): Sequence<T> =
    this.asSequence().treeWalkBreadthSequence(getter)

/**
 * Creates a recursive iterator, breadth first.
 */
class TreeWalkBreadthIterator<T>(val start: Iterator<T>, val getter: (T) -> Iterator<T>) : Iterator<T> {

    val toCheck = ArrayList<T>()
    var current: Iterator<T> = start

    override fun hasNext(): Boolean = current.hasNext()
    override fun next(): T {
        val item = current.next()

        toCheck.add(item)

        while (true) {
            if (current.hasNext()) {
                break
            } else if (toCheck.isNotEmpty()) {
                val newCheck = toCheck.removeAt(0)
                current = getter(newCheck)
            } else {
                break
            }
        }

        return item
    }
}
