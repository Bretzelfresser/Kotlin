import graph.Graph
import java.io.File
import kotlin.system.measureTimeMillis


val timeBefore = measureTimeMillis {Graph.Companion.parseGraph(getResourceLocation("germany.fmi"))}
//val GRAPH : Graph = Graph.Companion.parseGraph(getResourceLocation("germany.fmi"))

fun getAbsolutePath() : String{
    val directory = File("")
    return directory.absolutePath
}

fun getResourceLocation(fileName : String) : String{
    return getAbsolutePath() + "/src/main/kotlin/resources/" + fileName
}

fun main(args: Array<String>) {
    val seconds = timeBefore / 1000
    println("$seconds seconds")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")



}
