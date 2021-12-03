package graph

import java.io.File

// 0 1488520 147 9 130

class Graph() {

    /*
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Graph

        if (amountNodes != other.amountNodes) return false
        if (amountEdges != other.amountEdges) return false
        if (!nodeList.contentEquals(other.nodeList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = amountNodes
        result = 31 * result + amountEdges.hashCode()
        result = 31 * result + nodeList.contentHashCode()
        return result
    }

     */


    companion object {

        var numNodes = -1
        var numEdges = -1

        var lat = DoubleArray(0)
        var lon = DoubleArray(0)
        var predecessor = IntArray(0)
        var nodeWeight = IntArray(0)

        //TODO:
        // Aufbau: [ startNode1, endNode1, cost1, startNode2, endNode2, cost2, startNode3, endNode3, cost3, ... ]
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
         * Searches with binary search in the edgeList for all the outgoing edges.
         * Returns an Iterable of Pairs structured as follows: Pair<targetNodeId, cost>.
         */
        fun getOutgoingEdges(nodeId : Int) : IntArray {
            return getOutgoingEdges(nodeId, 0, Graph.numEdges - 1)
        }


        private fun getOutgoingEdges(nodeId : Int, start : Int, end : Int) : IntArray {

            val middle : Int = ((end - start) / 2) + start
            //println("startNode: ${edgeList[2 * start]}, middleNode: ${edgeList[2 * middle]}, endNode: ${edgeList[2 * end]}")
            //println("start: ${start}, middle: ${middle}, end: ${end}")

            return if ( edgeList[3 * end] - edgeList[3 * start] <= 10) {
                sequentialSearch(nodeId, start, end)
            } else if ( edgeList[3 * middle] < nodeId ) {
                getOutgoingEdges(nodeId, middle, end )
            } else {
                getOutgoingEdges(nodeId, start, middle)
            }
        }

        // letztes Stück sequentiell suchen
        private fun sequentialSearch(nodeId: Int, start: Int, end: Int): IntArray {

            val outgoingEdges = LinkedList<Int>()
            var foundEdges = 0
            var tempNode = start - 11       // weil max outgoingEdges ist 11
            if (tempNode < 0) {
                tempNode = 0
            }

            while (tempNode <= end + 11) {       // weil max outgoingEdges ist 11
                var performance = 3 * tempNode
                if (edgeList[performance] == nodeId) {
                    outgoingEdges.add(edgeList[performance + 1])
                    outgoingEdges.add(edgeList[performance + 2])
                    foundEdges++
                }

                // increment
                if (tempNode < numEdges - 1) {
                    tempNode++
                } else {
                    break
                }
            }

            return outgoingEdges.toIntArray()
        }

 */








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

