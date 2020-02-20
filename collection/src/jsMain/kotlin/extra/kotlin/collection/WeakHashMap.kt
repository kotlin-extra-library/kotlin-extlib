package extra.kotlin.collection

private var WeakHashMap_Ids:Int = 0
actual class WeakHashMap<K: Any, V> : MutableMap<K, V>{

    fun genId() = "weakmap-${WeakHashMap_Ids++}"
    var id = genId()

    override val size: Int
        get() = Int.MAX_VALUE

    override fun containsKey(key: K): Boolean {
        return (key.asDynamic()[id] as? Any).takeIf { it != undefined }  != null
    }

    override fun containsValue(value: V): Boolean = false

    override fun get(key: K): V? {
        val existing = (key.asDynamic()[id] as? Any).takeIf { it != undefined } as? V
        return existing
    }

    override fun isEmpty(): Boolean = false

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = mutableSetOf()
    override val keys: MutableSet<K> = mutableSetOf()
    override val values: MutableCollection<V> = mutableListOf()

    override fun clear() {
        id = genId()
    }

    override fun put(key: K, value: V): V? {
        val existing = (key.asDynamic()[id] as? Any).takeIf { it != undefined } as? V
        key.asDynamic()[id] = value
        return existing
    }

    override fun putAll(from: Map<out K, V>) {
        for((entry, value) in from){
            put(entry, value)
        }
    }

    override fun remove(key: K): V? {
        val existing = (key.asDynamic()[id] as? Any).takeIf { it != undefined } as? V
        key.asDynamic()[id] = null
        return existing
    }

}
