package graph

import java.io.File

class Graph() {

    companion object {

        var numNodes = -1
        var numEdges = -1

        var lat = DoubleArray(0)
        var lon = DoubleArray(0)
        var predecessor = IntArray(0)
        var nodeWeight = IntArray(0)

        // edgeList: [ endNode1, cost1, endNode2, cost2 startNode3, cost3, ... ]
        // edgeListPos speichert die position in der edgeList, an der die ausgehenden Kanten der node starten
        // edgeAmound speichert die Anzahl an ausgehenden Kanten der node
        var edgeList = IntArray(0)
        var edgeListPos = IntArray(0)
        var edgeAmount = IntArray(0)

        fun parseGraph(path : String) {

            // 0..5:    clear metadata
            // 5:       num of nodes
            // 6:       num of edges
            // 7..x:    id, ignore, lat, long, ignore
            // x..end:  srcId, targetId, cost, ignore, ignore

            // Read File
            var lineString : List<String>
            val reader = File(path).inputStream().bufferedReader()
            val iterator = reader.lines().iterator()  // lines = kokain lines

            // organize data structure (edgeList)
            var lineNumber = 0
            val offset = 7
            var lastNodeId : Int = 0
            var amountCurrentEdges : Int = 0
            while(iterator.hasNext()) {
                val line = iterator.next()
                lineNumber++

                // print current progress
                if (lineNumber%(Math.pow(10.0, 6.0).toInt()) == 0) {
                    // Console Prints
                    val percent = (lineNumber / ((numNodes + numEdges)/ 100) )
                    print (percent.toString() + "%: [")
                    for (i in 0..percent) { print("#") }
                    for (i in 0..100-percent){ print("-") }
                    print("] \n")
                }


                when (lineNumber) {
                    // metadata
                    in (0..5) -> {
                        continue
                    }
                    6 -> {
                        numNodes = line.toInt()

                        lat = DoubleArray(numNodes)
                        lon = DoubleArray(numNodes)
                        predecessor = IntArray(numNodes)
                        nodeWeight = IntArray(numNodes)
                        for (i in 0 until numNodes) {
                            nodeWeight[i] = Int.MAX_VALUE
                        }
                        edgeListPos = IntArray(numNodes)
                        edgeAmount = IntArray(numNodes)

                    }
                    7 -> {
                        numEdges = line.toInt()

                        //edgeCost = IntArray(numEdges)
                        edgeList = IntArray(3 * numEdges)
                    }
                    // Organize Nodes
                    in (8..numNodes+offset) -> {
                        lineString = line.split(" ")

                        lat[lineNumber - offset - 1] = lineString[2].toDouble()
                        lon[lineNumber - offset - 1] = lineString[3].toDouble()
                    }
                    // Organize Edges
                    else -> {
                        val lineString = line.split(" ")

                        val currentEdge = lineNumber - numNodes - offset - 1
                        var source = lineString[0].toInt()        // source

                        if (source == lastNodeId) {
                            amountCurrentEdges++
                        }
                        else {
                            edgeAmount[lastNodeId] = amountCurrentEdges
                            edgeListPos[source] = currentEdge

                            lastNodeId = source
                            amountCurrentEdges = 1
                        }

                        edgeList[2 * currentEdge] = lineString[1].toInt()          // target
                        edgeList[2 * currentEdge + 1] = lineString[2].toInt()      // cost

                    }
                }
            }
            reader.close()
        }


        fun getOutgoingEdges(nodeId : Int) : IntArray {
            var len = edgeAmount[nodeId]

            var intArray : IntArray = IntArray(2 * len)
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

}

