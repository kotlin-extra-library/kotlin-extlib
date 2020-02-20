package test.extra.kotlin.collections

import extra.kotlin.collection.ArrayListQueue
import kotlin.test.Test
import kotlin.test.assertTrue

class Collections {

    @Test
    fun testArrayListQueue(){
        val q = ArrayListQueue(listOf(1, 3, 5, 7))
        val output = ArrayList<Int>()
        while (q.isNotEmpty()){
            output.add(q.poll()!!)
        }
        assertTrue(output.containsAll(listOf(1, 3, 5, 7)))
    }
}
