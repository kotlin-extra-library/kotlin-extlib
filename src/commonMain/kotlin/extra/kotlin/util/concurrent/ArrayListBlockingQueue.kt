package extra.kotlin.util.concurrent

import extra.kotlin.util.collection.Queue


class ArrayListBlockingQueue<E>(): Queue<E>, AbstractMutableCollection<E>() {

    private val data = ArrayList<E>()
    private val mutex = Lock()

    // AbstractCollection methods
    override val size: Int
        get() = data.size

    override fun add(element: E) = mutex.use { data.add(element) }

    /**
     * Returns an iterator over the elements of this sequence that supports removing elements during iteration.
     * **WARNING** may break concurrency.
     */
    override fun iterator() = data.iterator()

    // Queue interface method
    override fun offer(e: E) {
        add(e)
    }

    override fun poll() = mutex.use { try {
        data.first().also { remove(it) }
    } catch (e: NoSuchElementException){
        null
    } }

    override fun element() = mutex.use { data.first() }

    override fun peek()= mutex.use { try {
        data.first()
    } catch (e: NoSuchElementException){
        null
    } }

}