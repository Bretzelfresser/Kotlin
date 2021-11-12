package graph

import java.io.File


data class Graph(val amountNodes : Int, val amountEdges : Int, val nodeList : Array<Node>) {

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

    companion object {

        fun parseGraph(path : String) : Graph {

            var numNodes : Int = 0
            var numEdges : Int = 0
            var adjacencyList = Array(numNodes) { _ -> Node(1, 1.0, 1.0) }


            // 0..5:    clear metadata
            // 5:       num of nodes
            // 6:       num of edges
            // 7..x:    id, ignore, lat, long, ignore
            // x..end:  srcId, targetId, cost, ignore, ignore

            // Read File
            var lineString : List<String>
            val reader = File(path).inputStream().bufferedReader()
            val iterator = reader.lines().iterator()  // lines = kokain lines

            // organize data structure (adjacencyList)
            var lineNumber = 0
            val offset = 7
            while(iterator.hasNext()) {
                val line = iterator.next()
                lineNumber++

                // print current progress
                if (lineNumber%(Math.pow(10.0, 5.0).toInt()) == 0)
                    println(((lineNumber / 1000) / ((numNodes + numEdges)/ 100000)).toString() + "%")

                // metadata
                if(lineNumber < 6)
                    continue
                if(lineNumber == 6){
                    numNodes = parseInt(line)
                    adjacencyList = Array(numNodes) { _ -> Node(1, 1.0, 1.0) }
                    continue
                }
                if (lineNumber == 7){
                    numEdges = parseInt(line)
                    continue
                }
                // Organize Nodes
                if(lineNumber < numNodes + offset) {
                    lineString = line.split(" ")

                    //println("index: " + (lineNumber - offset).toString() + ", linestring: " + lineString)
                    adjacencyList[lineNumber - offset - 1] = Node(
                        lineString[0].toInt(),
                        lineString[2].toDouble(),
                        lineString[3].toDouble()
                    )
                // Organize Edges
                } else {
                    val lineString = line.split(" ")

                    val edge = Edge(parseInt(lineString[0]), parseInt(lineString[1]), parseInt(lineString[2]), parseInt(lineString[3]), parseInt(lineString[4]))
                    adjacencyList[edge.preDecessor].addEdge(edge)
                }
            }
            reader.close()
            return Graph(numNodes, numEdges, adjacencyList)
        }

        /**
         *  fickt euch alle kotlin parse int developer
         */
        private fun parseInt(string : String) : Int{
            return string.toDouble().toInt()
        }
    }



}

