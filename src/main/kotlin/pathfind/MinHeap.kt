package pathfind

import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList

class MinHeap<T : Comparable<T>> {

    val collection : MutableList<T> = ArrayList<T>()

    fun add(element: T){

    }

    fun pop() : T{
        if (collection.size == 0){
            throw IllegalArgumentException("can pop from a heap with no elements")
        }
        return collection.removeAt(0)
    }

    private fun buildHeap(index : Int){

    }



}