package closestNode

import kotlin.math.pow
import kotlin.math.sqrt


fun getClosestNodeNaively(lat : Double, lon : Double, lats : DoubleArray, lons : DoubleArray) : Int{
    var min = Double.MAX_VALUE
    var minIdx = -1
    var distance = Double.MAX_VALUE
    for(i in lats.indices){
        distance = getDistanceSquared(lat, lon, lats[i], lons[i])
        if(distance < min){
            min = distance 
            minIdx = i
        }
    }
    distance = sqrt(distance)
    println("distance: $distance")
    return minIdx
}

fun getDistanceSquared(lat1 : Double, lon1 : Double, lat2 : Double, lon2 : Double) : Double{
    return (lat1-lat2).pow(2) + (lon1-lon2).pow(2)
}