package pathfind

import graph.Graph
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis

/**
 * graph Graph: A graph formatted as adjacency list.
 * startNode Int: The index of the starting node.
 */
class Dijkstra(val graph: Graph, val startNode : Int){

    val table = Array<TableEntry>(graph.amountNodes){ TableEntry(-1,-1,Int.MAX_VALUE) }

    init {
        println("dijkstra started")
        preProcess()
    }

    private fun preProcess(){
        val processedNodes: Array<Boolean> = Array<Boolean>(graph.amountNodes) { false }
        val tree = PriorityQueue<TableEntry>()
        val start = TableEntry(startNode, startNode, 0)

        var treeAdd : Long = 0
        var treeRemove : Long = 0
        var treePoll : Long = 0

        tree.add(start)
        table[startNode] = start
        var amountProcessed : Int = 0


        while (!tree.isEmpty()){
            val current : TableEntry
            treePoll += measureTimeMillis { current = tree.poll() }
            val currentNode = current.node;
            //iterate over all outgoing Edges
            for(edge in graph.nodeList[currentNode].outgoingEdges){

                //only update the node to the queue when not processed
                if(processedNodes[edge.successor])
                    continue
                val needWeight = edge.cost + current.neededWeight
                if(table[edge.successor].neededWeight == Int.MAX_VALUE){
                    val table = TableEntry(edge.successor, currentNode, needWeight)
                    this.table[edge.successor] = table
                    treeAdd += measureTimeMillis {tree.add(table)}
                }
                else if (needWeight < table[edge.successor].neededWeight){
                   table[edge.successor].neededWeight = needWeight
                    treeRemove += measureTimeMillis{ table[edge.successor] }
                    treeAdd += measureTimeMillis {tree.add(table[edge.successor])}

                }
            }
            processedNodes[currentNode] = true
            amountProcessed++
            if (amountProcessed%(Math.pow(10.0, 5.0).toInt()) == 0)
                println(String.format("%.1f", (amountProcessed / (graph.amountNodes / 100.0f))) + "%")
        }

        println("Add: $treeAdd")
        println("Remove: $treeRemove")
        println("Poll: $treePoll")
    }

    /**
     * returns a iterator that starts with the start Node and ends with the end Node
     */
    fun getPath(node : Int) : Iterator<Int>{
        if(!hasPath(node))
            throw IllegalArgumentException("there has to ba a path to that node")
        var currentNode = node
        val iterable = ArrayList<Int>()
        iterable.add(currentNode)
        while (currentNode != startNode){
            currentNode = table[currentNode].preDecessor
            iterable.add(currentNode)
        }
        iterable.reverse()
        return iterable.iterator();
    }

    fun hasPath(node : Int) : Boolean{
        return table[node].neededWeight != Int.MAX_VALUE
    }

    fun getWeightToNode(node : Int) : Int{
        return table[node].neededWeight
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

}