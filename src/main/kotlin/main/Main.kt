package main

import graph.Graph
import pathfind.Dijkstra
import closestNode.getClosestNodeNaively
import mapServer.MapServer
import java.io.BufferedReader
import java.io.FileReader
import java.util.*


var graphDirectory : String = ""
var lon : Double = 0.0
var lat : Double = 0.0
var quePath : String = ""
var sourceNodeId : Int = 0

/*
    HOW TO  COMPILE TO COMAND LINE:
     - navigate to the location of the Kotlin project folder e.g. "cd C:\Users\[user]\Dokumente\GitHub\Kotlin"
     - execute the gradle script using "gradlew jar"
     - navigate to the newly compiled jar-file in .\Kotlin\build\libs\Kotlin-2.0
     - execute the script with the directory of the graph data eg "java -jar Kotlin-2.0.jar -graph /home/[user]/germany.fmi"
 */


fun main(args: Array<String>) {


    // to test in IDE - edit on a personal basis
    if (args.isEmpty()) {
        graphDirectory = "C:\\Users\\asdf3\\OneDrive - bwedu\\Uni\\3. Semester\\Programmier Projekt\\germany.fmi"                //"C:\\Users\\asdf3\\OneDrive - bwedu\\Uni\\3. Semester\\Programmier Projekt\\germany.fmi"
        lon = 0.0
        lat = 0.0
        quePath = ""                        //"C:\\Users\\asdf3\\OneDrive - bwedu\\Uni\\3. Semester\\Programmier Projekt\\Benchs\\germany.que"
        sourceNodeId = 0
    }
    // read parameters (parameters are expected in exactly this order)
    else if (args.size == 2) {
        graphDirectory = args[1]
        /*lon = args[3].toDouble()
        lat = args[5].toDouble()
        quePath = args[7]
        sourceNodeId = args[9].toInt()
         */
    }

    Graph.parseGraph( graphDirectory )
    MapServer.setupServer()

}

// was used for part one
fun benchmark() {

    // run benchmarks
    // read graph here
    println("Reading graph file $graphDirectory")
    val graphReadStart = System.currentTimeMillis()
    Graph.parseGraph( graphDirectory )
    val graphReadEnd = System.currentTimeMillis()
    println("\tgraph read took " + (graphReadEnd - graphReadStart) + "ms")


    // find closest node here
    println("Finding closest node to coordinates $lon $lat")
    val nodeFindStart = System.currentTimeMillis()
    val closestNode = getClosestNodeNaively(lat, lon)
    val nodeFindEnd = System.currentTimeMillis()
    println("\tfinding node $closestNode at lat: ${Graph.lat[closestNode]}, lon: ${Graph.lon[closestNode]} took " + (nodeFindEnd - nodeFindStart) + "ms")


    // set oneToOneDistance to the distance from
    println("Running one-to-one Dijkstras for queries in .que file $quePath")
    val queStart = System.currentTimeMillis()
    try {
        BufferedReader(FileReader(quePath)).use { bufferedReader ->
            var currLine: String
            var oldSource = -1
            while (bufferedReader.readLine().also { currLine = it } != null) {
                val oneToOneSourceNodeId = currLine.substring(0, currLine.indexOf(" ")).toInt()
                val oneToOneTargetNodeId = currLine.substring(currLine.indexOf(" ") + 1).toInt()
                // oneToOneSourceNodeId to oneToOneSourceNodeId as computed by
                // the one-to-one Dijkstra
                if (oldSource != oneToOneSourceNodeId) {
                    println()
                    println("New source query from: $oneToOneSourceNodeId")
                    oldSource = oneToOneSourceNodeId
                    Dijkstra(oneToOneSourceNodeId)

                }
                println(Graph.nodeWeight[oneToOneTargetNodeId])
            }
        }
    } catch (e: Exception) {
        println("No more entries to execute")
    }
    val queEnd = System.currentTimeMillis()
    println("\tprocessing .que file took " + (queEnd - queStart) + "ms")


    // run one-to-all Dijkstra here
    println("Computing one-to-all Dijkstra from node id $sourceNodeId")
    val oneToAllStart = System.currentTimeMillis()
    Dijkstra(sourceNodeId)
    val oneToAllEnd = System.currentTimeMillis()
    println("\tone-to-all Dijkstra took " + (oneToAllEnd - oneToAllStart) + "ms")


    // set oneToAllDistance to the distance from main.getSourceNodeId to
    // ask user for a target node id
    print("Enter target node id... ")
    val targetNodeId = Scanner(System.`in`).nextInt()
    val oneToAllDistance = Graph.nodeWeight[targetNodeId]
    // targetNodeId as computed by the one-to-all Dijkstra
    println("Distance from $sourceNodeId to $targetNodeId is $oneToAllDistance")

}

