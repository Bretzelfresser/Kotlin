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

        var staticIsFun : Int = 0

        fun parseGraph(path : String) : Graph{

        var numNodes : Int = 0
        var numEdges : Int = 0


            // 0..5:    clear metadata
            // 5:       num of nodes
            // 6:       num of edges
            // 7..?:    id, ignore, lat, long, ignore
            val text = File(path).readText()
            val lineList = text.split("\n")

            try {
                numNodes = lineList[5].toInt()
                numEdges = lineList[6].toInt()
            } catch (nfe: NumberFormatException) {
                // not a valid int
            }
            var adjacencyList = Array(numNodes) { Node(1, 0.0, 0.0) }

            var lineString : List<String>
            val offset : Int = 6
            for(i in 0..numNodes+1) {
                lineString = lineList[i + offset].split(" ")

                println(lineString)

                adjacencyList[i + offset] = Node(
                    lineString[0].toInt(),
                    lineString[2].toDouble(),
                    lineString[3].toDouble() )
            }



            return Graph(numNodes, numEdges, adjacencyList)
        }
    }



}

