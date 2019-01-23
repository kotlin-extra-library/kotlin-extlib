package extra.kotlin.util.collection


class ArrayListQueue<E>(): Queue<E>, AbstractMutableCollection<E>() {

    private val data = ArrayList<E>()

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
