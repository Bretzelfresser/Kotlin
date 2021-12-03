package pathfind

import graph.Graph
import java.util.*
import kotlin.system.measureTimeMillis


/**
 * graph Graph: A graph formatted as adjacency list.
 * startNode Int: The index of the starting node.
 */
class Dijkstra(val startId : Int) {



    //val table = Array<TableEntry>(Graph.numNodes){ TableEntry(-1,-1,Int.MAX_VALUE) }

    init {
        println("dijkstra started")
        preProcess()
    }

    /*
    private fun preProcess(){

        val compareSecond = compareBy<Pair<Int, Int>> { it.second }
        val priorityQueue = PriorityQueue<Pair<Int, Int>>(compareSecond)

        val processedNodes: BooleanArray = BooleanArray( Graph.numNodes )

        var prioAdd : Long = 0
        var prioRemove : Long = 0
        var prioPoll : Long = 0
        var amountProcessed : Int = 0

        // Pair< id : Int, distance : Int>
        val startPair = Pair<Int, Int>(startId, 0)
        Graph.nodeWeight[startId] = 0
        priorityQueue.add(startPair)

        while (!priorityQueue.isEmpty()) {
            val currentNode : Pair<Int, Int>
            prioPoll += measureTimeMillis { currentNode = priorityQueue.poll() }

            //iterate over all outgoing Edges
            val outgoingEdges = Graph.getOutgoingEdges(currentNode.first)
            var iter = outgoingEdges.iterator()
            while (iter.hasNext()) {

                var targetNode = iter.next()
                var edgeCost = iter.next()
                var targetNodeWeight = Graph.nodeWeight[targetNode]
                //only update the node to the queue when not processed
                if(processedNodes[targetNode])
                    continue
                val newWeight = edgeCost + targetNodeWeight
                if (targetNodeWeight == Int.MAX_VALUE) {
                    targetNodeWeight = newWeight
                    prioAdd += measureTimeMillis {priorityQueue.add( Pair(targetNode, newWeight) )}
                }
                else if (newWeight < targetNodeWeight) {
                    prioRemove += measureTimeMillis{ priorityQueue.add(Pair(targetNode, targetNodeWeight)) }
                    targetNodeWeight = newWeight
                    prioAdd += measureTimeMillis {priorityQueue.add(Pair(targetNode, targetNodeWeight))}

                }
            }
            processedNodes[currentNode.first] = true
            amountProcessed++
            if (amountProcessed%(Math.pow(10.0, 5.0).toInt()) == 0)
                println(String.format("%.1f", (amountProcessed / (Graph.numNodes / 100.0f))) + "%")
        }

        println("Add: $prioAdd")
        println("Remove: $prioRemove")
        println("Poll: $prioPoll")
    }

     */








    private fun preProcess(){

        val compareSecond = compareBy<Pair<Int, Int>> { it.second }
        val priorityQueue = PriorityQueue<Pair<Int, Int>>(compareSecond)
        //val priorityTree = Tree()

        val processedNodes : BooleanArray = BooleanArray( Graph.numNodes )

        var prioAdd : Long = 0
        var prioRemove : Long = 0
        var prioPoll : Long = 0
        var amountProcessed : Int = 0

        // Pair< id : Int, distance : Int>
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
                if(processedNodes[targetNode])
                    continue
                else {
                    newWeight = edgeCost + targetNodeWeight
                    if (newWeight < targetNodeWeight) {
                        //prioRemove += measureTimeMillis{ priorityTree.add(TreeNode(targetNode, targetNodeWeight)) }
                        targetNodeWeight = newWeight
                        prioAdd += measureTimeMillis { priorityQueue.add( Pair(targetNode, targetNodeWeight)) }
                    }
                }

            }
            processedNodes[currentNode.first] = true


            amountProcessed++
            if (amountProcessed%(Math.pow(10.0, 5.0).toInt()) == 0)
                println(String.format("%.1f", (amountProcessed / (Graph.numNodes / 100.0f))) + "%")

        }

        println("Add: $prioAdd")
        println("Remove: $prioRemove")
        println("Poll: $prioPoll")
    }












    /*




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



     */



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

