package pathfind

import graph.Graph
import java.util.*
import kotlin.system.measureTimeMillis


/**
 * graph Graph: A graph formatted as adjacency list.
 * startNode Int: The index of the starting node.
 */
class Dijkstra(val startId : Int) {


    init {
        //println("dijkstra started")
        preProcess()
    }

    private fun preProcess(){

        val compareSecond = compareBy<Pair<Int, Int>> { it.second }
        val priorityQueue = PriorityQueue<Pair<Int, Int>>(compareSecond)

        val processedNodes = BooleanArray( Graph.numNodes ) { false }

        var prioAdd : Long = 0
        var prioPoll : Long = 0
        //var amountProcessed : Int = 0

        for (i in Graph.nodeWeight.indices) {
            Graph.nodeWeight[i] = Int.MAX_VALUE
        }
        val startPair = Pair(startId, 0)
        Graph.nodeWeight[startId] = 0
        priorityQueue.add(startPair)


        // Schleifenvariablen deklarieren
        var outgoingEdges : IntArray
        var targetNode : Int
        var edgeCost : Int
        var targetNodeWeight : Int
        var newWeight : Int
        var currentNode : Pair<Int, Int>



        while (!priorityQueue.isEmpty()) {
            prioPoll += measureTimeMillis { currentNode = priorityQueue.poll() }

            //iterate over all outgoing Edges
            outgoingEdges = Graph.getOutgoingEdges(currentNode.first)
            for (i in 0 until outgoingEdges.size / 2) {

                targetNode = outgoingEdges[2 * i]
                edgeCost = outgoingEdges[2 * i + 1]
                targetNodeWeight = Graph.nodeWeight[targetNode]
                //only update the node to the queue when not processed
                if (processedNodes[targetNode])
                    continue
                else {
                    newWeight = edgeCost + Graph.nodeWeight[currentNode.first]
                    if (targetNodeWeight == Int.MAX_VALUE) {
                        Graph.nodeWeight[targetNode] = newWeight
                        prioAdd += measureTimeMillis {priorityQueue.add( Pair(targetNode, newWeight) )}
                    }
                    else if (newWeight < targetNodeWeight) {
                        //prioRemove += measureTimeMillis{ priorityQueue.remove(Pair(targetNode, targetNodeWeight)) }
                        Graph.nodeWeight[targetNode] = newWeight
                        prioAdd += measureTimeMillis { priorityQueue.add( Pair(targetNode, newWeight)) }
                    }
                }

            }
            processedNodes[currentNode.first] = true

            /*
            // print progress
            amountProcessed++
            if (amountProcessed%(Math.pow(10.0, 6.0).toInt()) == 0) {
                val percent = (amountProcessed / (Graph.numNodes / 100.0f)).toInt()
                print(String.format("%.1f", (amountProcessed / (Graph.numNodes / 100.0f))) + "% [")
                for (i in 0..percent) { print("#") }
                for (i in 0..100-percent){ print("-") }
                print("] Task: Processing Dijkstra. \n")
            }

             */

        }

        /*println("Add: $prioAdd")
        println("Remove: $prioRemove")
        println("Poll: $prioPoll")*/
    }

}

