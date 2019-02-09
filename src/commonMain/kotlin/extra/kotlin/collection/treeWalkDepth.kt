package extra.kotlin.collection

/**
 * Creates a recursive sequence, depth first.
 */
fun <T> Sequence<T>.treeWalkDepthSequence(getter: (T) -> Sequence<T>) = object : Sequence<T> {
    override fun iterator(): Iterator<T> = TreeWalkDepthIterator(this@treeWalkDepthSequence.iterator()) { getter(it).iterator() }
}

/**
 * Creates a recursive sequence, depth first.
 */
fun <T> Iterable<T>.treeWalkDepthSequence(getter: (T) -> Sequence<T>): Sequence<T> =
    this.asSequence().treeWalkDepthSequence(getter)

/**
 * Creates a recursive iterator, depth first.
 */
class TreeWalkDepthIterator<T>(val start: Iterator<T>, val getter: (T) -> Iterator<T>) : Iterator<T> {

    val stack = arrayListOf<Iterator<T>>(start)
    val current get() = stack.lastOrNull()

    override fun hasNext(): Boolean = current != null
    override fun next(): T {

        if(current == null) throw NoSuchElementException()
        while(current?.hasNext() == false){
            stack.removeAt(stack.lastIndex)
        }
        if(current == null) throw NoSuchElementException()

        val item = current!!.next()
        stack.add(getter(item))

        while(current?.hasNext() == false){
            stack.removeAt(stack.lastIndex)
        }

        return item
    }
}
