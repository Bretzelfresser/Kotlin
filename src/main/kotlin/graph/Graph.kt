package graph

import java.io.*
import kotlin.math.pow

class Graph {

    companion object {

        var numNodes = -1
        private var numEdges = -1
        const val offset = 7

        var lat = DoubleArray(0)
        var lon = DoubleArray(0)
        private var predecessor = IntArray(0)
        var nodeWeight = IntArray(0)

        // edgeList: [ endNode1, cost1, endNode2, cost2 endNode3, cost3, ... ]
        // edgeListPos speichert die position in der edgeList, an der die ausgehenden Kanten der node starten
        // edgeAmound speichert die Anzahl an ausgehenden Kanten der node
        var edgeList = IntArray(0)
        var edgeListPos = IntArray(0)
        var edgeAmount = IntArray(0)

        var PATH = ""
        var progress : Int = 0

        fun parseGraph(path : String) {

            // 0..5:    clear metadata
            // 5:       num of nodes
            // 6:       num of edges
            // 7..x:    id, ignore, main.getLat, long, ignore
            // x..end:  srcId, targetId, cost, ignore, ignore

            // Read File
            PATH = path
            val reader = File(path).inputStream().bufferedReader()
            val iterator = reader.lines().iterator()  // lines = kokain lines

            // read meta data and setup of data structure
            for (i in (1..7)) {

                val line = iterator.next()

                when (i) {
                    in (0..5) -> {
                        continue
                    }
                    6 -> {
                        numNodes = line.toInt()

                        lat = DoubleArray(numNodes)
                        lon = DoubleArray(numNodes)
                        predecessor = IntArray(numNodes)
                        nodeWeight = IntArray(numNodes)
                        for (j in 0 until numNodes) {
                            nodeWeight[j] = Int.MAX_VALUE
                        }
                        edgeListPos = IntArray(numNodes)
                        edgeAmount = IntArray(numNodes)

                    }
                    7 -> {
                        numEdges = line.toInt()

                        edgeList = IntArray(3 * numEdges)
                    }
                }
            }

            // serialize nodes and edges simultaneously
            val threadEdges : Thread = ThreadedEdges(0, numEdges)
            threadEdges.start()
            val threadNodes : Thread = ThreadedNodes(0, numNodes)
            threadNodes.start()

            // print current progress
            while (threadEdges.isAlive and threadNodes.isAlive) {

                if (progress%(12.0.pow(6.0).toInt()) == 0) {
                    val percent = (progress / ( (numNodes + numEdges) / 100.0f)).toInt()
                    print(String.format("%.1f", (progress / ( (numNodes + numEdges) / 100.0f))) + "% [")
                    for (i in 0..percent) { print("#") }
                    for (i in 0..100-percent){ print("-") }
                    print("] Task: Serializing Graph-Data. \n")
                }
            }

            // wait for threads to die
            threadNodes.join()
            threadEdges.join()

            reader.close()
        }


        fun getOutgoingEdges(nodeId : Int) : IntArray {
            val len = edgeAmount[nodeId]

            val intArray = IntArray(2 * len)
            for (i in 0 until len) {
                intArray[2 * i] = edgeList[ (2 * edgeListPos[nodeId]) + (2 * i) ]
                intArray[2 * i + 1] = edgeList[ (2 * edgeListPos[nodeId]) + (2 * i) + 1 ]
            }
            return intArray
        }

        /*
        /**
         *  fickt euch alle kotlin parse int developer
         */
        private fun parseInt(string : String) : Int {
            return string.toInt()
        }
         */

    }

    class ThreadedNodes(private val startLine: Int, private val endLine : Int) : Thread() {

        override fun run() {

            var threadedLine = startLine
            var lineString : List<String>      // lines = kokain lines

            // jump to startLine
            val reader : BufferedReader = File(PATH).bufferedReader()
            val iterator = reader.lines().skip( (offset + startLine).toLong()).iterator()

            for (i in 0 until (endLine - startLine)) {
                val line = iterator.next()
                progress++

                lineString = line.split(" ")

                lat[threadedLine] = lineString[2].toDouble()
                lon[threadedLine] = lineString[3].toDouble()

                threadedLine++
            }
            reader.close()
        }
    }

    class ThreadedEdges(private val startLine: Int, private val endLine : Int) : Thread() {

        override fun run() {

            var threadedLine = startLine
            var lastNodeId = 0
            var amountCurrentEdges = 0

            // jump to startLine
            val reader: BufferedReader = File(PATH).bufferedReader()
            val iterator = reader.lines().skip((offset + numNodes + startLine).toLong()).iterator()

            for (i in 0 until (endLine - startLine)) {
                val line = iterator.next()
                progress++

                val lineString = line.split(" ")

                val currentEdge = threadedLine
                val source = lineString[0].toInt()      // source

                if (source == lastNodeId) {
                    amountCurrentEdges++
                } else {
                    edgeAmount[lastNodeId] = amountCurrentEdges
                    edgeListPos[source] = currentEdge

                    lastNodeId = source
                    amountCurrentEdges = 1
                }
                edgeList[2 * currentEdge] = lineString[1].toInt()          // target
                edgeList[2 * currentEdge + 1] = lineString[2].toInt()      // cost

                threadedLine++
            }
            reader.close()
        }
    }


}



