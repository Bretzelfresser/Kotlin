package pathfind

import graph.Graph
import java.util.*
import kotlin.collections.ArrayList

/**
 * graph Graph: A graph formatted as adjacency list.
 * startNode Int: The index of the starting node.
 */
class Dijkstra(val graph: Graph, val startNode : Int){
    val processedNodes: MutableSet<Int> = mutableSetOf()
    val deque = PriorityQueue<Int>()
    val table = Array<Pair<Int, Double>>(graph.amountNodes){ Pair(0, Double.POSITIVE_INFINITY) }

    init {
        println("init called")
        deque.add(startNode)
        table[startNode] = Pair(startNode, 0.0)
        preProcess()
    }

    private fun preProcess(){
        while (!deque.isEmpty()){
            val currentWeight = getWeight(deque.peek())
            val current = deque.poll()
            //iterate over all outgoing Edges
            for(edge in graph.nodeList[current].outgoingEdges){
                val needWeight = edge.cost + currentWeight
                //only update the node to the queue when not processed
                if (!processedNodes.contains(edge.successor) &&  needWeight < getWeight(edge.successor)){
                    deque.add(edge.successor)
                    table[edge.successor] = Pair(current, needWeight)
                }
            }
            processedNodes.add(current)
            val processed = processedNodes.size
            if (processed%(Math.pow(10.0, 5.0).toInt()) == 0)
                println(((processed / 1000) / ((graph.amountNodes)/ 100000)).toString() + "%")
        }
    }

    /**
     * returns a iterator that starts with the start Node and ends with the end Node
     */
    fun getPath(node : Int) : Iterator<Int>{
        var currentNode = node
        val iterable = ArrayList<Int>()
        iterable.add(currentNode)
        while (currentNode != startNode){
            currentNode = table[currentNode].first
            iterable.add(currentNode)
        }
        iterable.reverse()
        return iterable.iterator();
    }

    private fun getWeight(node : Int) : Double{
        if(node >= graph.amountNodes)
            return Double.POSITIVE_INFINITY
       return table[node].second
    }

    /*Funktion Dijkstra(Graph, Startknoten):
          initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q)
          solange Q nicht leer:                       // Der eigentliche Algorithmus
              u:= Knoten in Q mit kleinstem Wert in abstand[]
              entferne u aus Q                                // für u ist der kürzeste Weg nun bestimmt
              für jeden Nachbarn v von u:
                  falls v in Q:                            // falls noch nicht berechnet
                     distanz_update(u,v,abstand[],vorgänger[])   // prüfe Abstand vom Startknoten zu v
          return vorgänger[]



    Methode initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q):
          für jeden Knoten v in Graph:
              abstand[v]:= unendlich
              vorgänger[v]:= null
          abstand[Startknoten]:= 0
          Q:= Die Menge aller Knoten in Graph



    Methode distanz_update(u,v,abstand[],vorgänger[]):
          alternativ:= abstand[u] + abstand_zwischen(u, v)   // Weglänge vom Startknoten nach v über u
          falls alternativ < abstand[v]:
              abstand[v]:= alternativ
              vorgänger[v]:= u
    */

    // track used nodes
    // track reachable nodes -> sort in Heap
    // track predecessors
    // track length
    //
}