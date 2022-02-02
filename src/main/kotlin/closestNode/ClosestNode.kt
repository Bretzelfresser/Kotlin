package closestNode

import graph.Graph
import kotlin.math.pow
import kotlin.math.sqrt


fun getClosestNodeNaively(lat : Double, lon : Double) : Int{
    val lats = Graph.lat
    val lons = Graph.lon

    var min = Double.MAX_VALUE
    var minIdx = -1
    var distance : Double = Double.MAX_VALUE

    for(i in lats.indices){
        distance = getDistanceSquared(lat, lon, lats[i], lons[i])
        if (distance < min) {
            min = distance 
            minIdx = i
        }
    }
    println("index is: $minIdx")
    distance = sqrt(distance)
    println("distance: $distance")
    return minIdx
}

/*fun getClosestNodeNaively(id : Int) : Int {
    val lats = Graph.main.getLat
    val lons = Graph.main.getLon

    var min = Double.MAX_VALUE
    var minIdx = -1
    var secondMinIdx = -1
    var distance = Double.MAX_VALUE

    for(i in lats.indices){
        distance = getDistanceSquared(Graph.main.getLat[id], Graph.main.getLon[id], lats[i], lons[i])
        if (distance < min) {
            min = distance
            secondMinIdx = minIdx
            minIdx = i
        }
    }
    distance = sqrt(distance)
    //println("distance: $distance")
    return secondMinIdx
}*/

fun getDistanceSquared(lat1 : Double, lon1 : Double, lat2 : Double, lon2 : Double) : Double{
    return (lat1-lat2).pow(2) + (lon1-lon2).pow(2)
}