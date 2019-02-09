package test.extra.kotlin.collection

import extra.kotlin.collection.contentEquals
import extra.kotlin.collection.treeWalkBreadthSequence
import extra.kotlin.collection.treeWalkDepthSequence
import kotlin.test.Test
import kotlin.test.assertTrue

class TreeWalkTest{


    @Test
    fun treeBreadthWalk(){
        val subject = listOf(
            1,
            2,
            listOf(
                4,
                listOf(
                    7,
                    8,
                    9
                ),
                5,
                6
            ),
            3
        )
        val list = subject
            .treeWalkBreadthSequence<Any?> { if(it is Iterable<*>) it.asSequence() else sequenceOf() }
            .mapNotNull { it as? Int }
            .toList()
        println(list.joinToString { it.toString() })
        assertTrue { list contentEquals (1 .. 9) }
    }

    @Test
    fun treeDepthWalk(){
        val subject = listOf(
            1,
            2,
            listOf(
                3,
                4,
                listOf(
                    5,
                    6,
                    7
                ),
                8
            ),
            9
        )
        val list = subject
            .treeWalkDepthSequence<Any?> { if(it is Iterable<*>) it.asSequence() else sequenceOf() }
            .mapNotNull { it as? Int }
            .toList()
        println(list.joinToString { it.toString() })
        assertTrue { list contentEquals (1 .. 9) }
    }
}
