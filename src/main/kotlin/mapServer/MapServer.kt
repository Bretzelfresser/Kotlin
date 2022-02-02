package mapServer

import closestNode.getClosestNodeNaively
import com.sun.net.httpserver.HttpContext
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import graph.Graph
import pathfind.Dijkstra
import java.io.File
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.InetSocketAddress
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Executor
import java.util.logging.Handler

class MapServer {

    companion object {

        var path = "html\\html test.html"

        val server = HttpServer.create(InetSocketAddress(8080), 0)
        fun setupServer()
        {
            println("setting up server...")
            contextMain()
            contextClosestNode()
            server.start()
            println("server started!")
        }

        fun contextMain() {
            println("creating main context...")
            server.createContext("/") { exchange: HttpExchange ->
                exchange.sendResponseHeaders(200, File(path).length())
                val output = exchange.responseBody
                output.write(File(path).readBytes())
                output.close()
            }
        }

        fun contextClosestNode() {
            println("creating closest node context...")
            server.createContext("/getClosestNode") { exchange: HttpExchange ->
                val parms = queryToMap(exchange.requestURI.query)
                var closestNode = getClosestNodeNaively(parms["lat"]!!.toDouble(), parms["lon"]!!.toDouble())
                var response = ""+Graph.lat[closestNode]+","+Graph.lon[closestNode]+","
                val output = exchange.responseBody

                if (parms["amountMarkers"]!!.toInt() == 0) {
                    println("starting Dijkstra...")
                    Dijkstra(closestNode);
                    println("finished Dijkstra")

                    exchange.sendResponseHeaders(200, response.toByteArray(StandardCharsets.UTF_8).size.toLong())
                    output.write(response.toByteArray(StandardCharsets.UTF_8))
                }
                else if (parms["amountMarkers"]!!.toInt() == 1) {

                    var currentPath = Graph.returnPath(closestNode)

                    while (!currentPath.isEmpty()) {
                        response += Graph.lat[currentPath[0]].toString() + ","
                        response += Graph.lon[currentPath[0]].toString() + ","
                        currentPath.removeFirst()
                    }
                    exchange.sendResponseHeaders(200, response.toByteArray(StandardCharsets.UTF_8).size.toLong())
                    output.write(response.toByteArray(StandardCharsets.UTF_8))
                }
                else throw IllegalArgumentException("amountMarkers have to be 0 or 1!");
                //println(response)

                output.close()
            }
        }


        fun queryToMap(query: String): Map<String, String> {
            val result: MutableMap<String, String> = HashMap()
            for (param in query.split("&").toTypedArray()) {
                val pair = param.split("=").toTypedArray()
                if (pair.size > 1) {
                    result[pair[0]] = pair[1]
                } else {
                    result[pair[0]] = ""
                }
            }
            return result
        }


    }
}