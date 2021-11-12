import graph.Graph
import pathfind.Dijkstra
import java.io.File
import kotlin.system.measureTimeMillis

val GRAPH : Graph
val serializeGraphTime = measureTimeMillis {GRAPH = Graph.Companion.parseGraph(getResourceLocation("germany.fmi"))}
val processDijkstra = measureTimeMillis { Dijkstra(GRAPH, 0) }
//val GRAPH : Graph = Graph.Companion.parseGraph(getResourceLocation("germany.fmi"))

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

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")



}
