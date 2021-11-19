import graph.Graph
import pathfind.Dijkstra
import java.io.File
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

val GRAPH : Graph
val DIJKSTRA : Dijkstra
val serializeGraphTime = measureTimeMillis {GRAPH = Graph.Companion.parseGraph(getResourceLocation("germany.fmi"))}
val processDijkstra = measureTimeMillis {DIJKSTRA = Dijkstra(GRAPH, 8371825) }

fun getAbsolutePath() : String{
    val directory = File("")
    return directory.absolutePath
}

fun getResourceLocation(fileName : String) : String{
    return getAbsolutePath() + "/src/main/kotlin/resources/" + fileName
}

fun main(args: Array<String>) {
    val seconds = serializeGraphTime / 1000
    val secondsDijkstra = processDijkstra / 1000
    println("$seconds seconds to serialize the graph")
    println("$secondsDijkstra seconds needed to process Dijkstra")
    println(DIJKSTRA.getWeightToNode(16743651))



}
