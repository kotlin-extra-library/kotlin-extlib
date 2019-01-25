//package extra.kotlin.collection
//
//class SortedMap<K, V>(val comparator: Comparator<K>): MutableMap<K, V> {
//
//    private val map = HashMap<K, V>()
//    private val array = ArrayList<K>()
//
//    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
//        get() = array.toMutableSet()
//    override val keys: MutableSet<K>
//        get() = array.map { it.key }.toMutableSet()
//    override val size: Int
//        get() = map.size
//    override val values: MutableCollection<V>
//        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//
//    override fun clear() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun containsKey(key: K): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun containsValue(value: V): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun get(key: K): V? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun isEmpty(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun put(key: K, value: V)
//            = map.put(key, value).also { array.add(key); array.sortWith(comparator) }
//
//    override fun putAll(from: Map<out K, V>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun remove(key: K): V? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}