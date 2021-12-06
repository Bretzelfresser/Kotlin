import graph.Graph
import pathfind.Dijkstra
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis
import closestNode.getClosestNodeNaively
val serializeGraphTime = measureTimeMillis { Graph.parseGraph(getResourceLocation("germany.fmi"))}
val processDijkstra = measureTimeMillis { Dijkstra(8371825) }
val closestNode : Int
val closestNodeTime = measureTimeMillis { closestNode = getClosestNodeNaively(53.51864840000000239, 10.01621140000000132, Graph.lat, Graph.lon)}

fun getAbsolutePath() : String{
    val directory = File("")
    return directory.absolutePath
}

fun getResourceLocation(fileName : String) : String{
    return getAbsolutePath() + "/src/main/kotlin/resources/" + fileName
}

fun main(args: Array<String>) {
    val seconds = serializeGraphTime / 1000.0
    val secondsDijkstra = processDijkstra / 1000.0
    val secondsClosestNode = closestNodeTime / 1000.0
    println("$seconds seconds to serialize the graph")
    println("$secondsDijkstra seconds needed to process Dijkstra")
    val l2 = Graph.lon[closestNode]
    val l1 = Graph.lat[closestNode]
    println("$secondsClosestNode seconds needed to find the closest Node, it is $closestNode at $l1, $l2")


    //println(Graph.nodeWeight[434859] )
    println(Graph.nodeWeight[16743651] )
    println(Graph.nodeWeight[16743652] )
    println(Graph.nodeWeight[16743653] )
    println(Graph.nodeWeight[16743654] )
    println(Graph.nodeWeight[16743655] )
    println(Graph.nodeWeight[16743656] )
    println(Graph.nodeWeight[16743657] )
    println(Graph.nodeWeight[16743658] )
    println(Graph.nodeWeight[16743659] )
    println(Graph.nodeWeight[16743660] )

    /*printNode(0)
    printNode(1)
    printNode(3)
    printNode(20)
    printNode(60)
    printNode(1000)
    printNode(20000)*/

    /*var treeNodes = LinkedList<TreeNode>()
    var nums = arrayListOf<Int>(5, 3, 5, 4, 1, 2, 5, 3, 23, 7, 4, 3, 1)
    for (i in nums.indices) {
        treeNodes.add(TreeNode(i, nums[i]))
    }
    var tree = Tree()
    tree.root = treeNodes[0]
    for (i in 1 until nums.size) {
        tree.add(treeNodes[i])
    }
    var output = LinkedList<Int>()
    for (i in 0 until nums.size) {
        println(tree.poll().value)
    }*/


}



fun printNode(node : Int) {
    println("$node -> ")
    var temp = Graph.getOutgoingEdges(node)
    var iterator = temp.iterator()
    while (iterator.hasNext()) {
        println( " -> " + iterator.next().toString() + ", " + iterator.next().toString() )
    }
}
