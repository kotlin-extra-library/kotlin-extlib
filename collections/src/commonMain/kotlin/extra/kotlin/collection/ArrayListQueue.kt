package extra.kotlin.collection

/**
 * A simple FIFO queue backed by an [ArrayList]
 */
open class ArrayListQueue<E>(): Queue<E>, AbstractMutableCollection<E>() {

    protected val data = ArrayList<E>()

    constructor(collection: Collection<E>): this(){
        collection.addTo(data)
    }

    // AbstractCollection methods
    override val size: Int
        get() = data.size

    override fun add(element: E) = data.add(element)

    override fun iterator() = data.iterator()

    // Queue interface method
    override fun offer(e: E) { add(e) }

    override fun poll()
            = if(isEmpty()) null else element().also { remove(it) }

    override fun element() = data.first()

    override fun peek()
            = if(isEmpty()) null else element()

}
