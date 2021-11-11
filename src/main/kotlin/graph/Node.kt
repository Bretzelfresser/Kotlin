package graph

import java.util.LinkedList

data class Node(val id : Int, val lat : Double, val lon : Double) : Comparable<Node>{
    var outgoingEdges = LinkedList<Edge>()

    override fun compareTo(other : Node) : Int {
        if(other.id > this.id)
            return -1
        if(other.id < this.id)
            return 1
        return 0
    }

    fun addEdge(edge : Edge){
        outgoingEdges.add(edge)
    }

    fun addEdge(successor : Int, type : Int, maxSpeed : Int){
        addEdge(Edge(this.id, successor, type, maxSpeed))
    }
}