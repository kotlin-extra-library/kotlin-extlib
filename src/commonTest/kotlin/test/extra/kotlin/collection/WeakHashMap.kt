package test.extra.kotlin.collection

import extra.kotlin.collection.WeakHashMap
import kotlin.test.Test
import kotlin.test.assertTrue

class WeakHashMapTest {
    data class AdvancedKey(val a: Int = 0)

    @Test
    fun worksAsNormalMap() {
        val keyA = AdvancedKey(0)
        val keyB = AdvancedKey(1)
        val map = WeakHashMap<AdvancedKey, Int>()
        map[keyA] = 0
        assertTrue("a-0 for $map") { map[keyA] == 0 }
        map[keyA] = 1
        assertTrue("a-1 for $map") { map[keyA] == 1 }
        assertTrue("b-null for $map") { map[keyB] == null }
        map[keyB] = 8
        assertTrue("b-8 for $map") { map[keyB] == 8 }
    }
}
