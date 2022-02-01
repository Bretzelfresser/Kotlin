package mapServer

import closestNode.getClosestNodeNaively
import com.sun.net.httpserver.HttpContext
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import graph.Graph
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executor

class MapServer {

    companion object {
        val server = HttpServer.create(InetSocketAddress(8080), 0)
        fun setupServer()
        {
            println("setting up server...")
            server.createContext("/getClosestNode") { exchange: HttpExchange ->
                val parms = MapServer.queryToMap(exchange.requestURI.query)
                val input = exchange.requestBody

                var closestNode = getClosestNodeNaively(parms["lon"]!!.toDouble(), parms["lat"]!!.toDouble())
                println(closestNode)
                var response = "closest Node is at lon: " + Graph.lon[closestNode] + ", lat: " + Graph.lat[closestNode]


                exchange.sendResponseHeaders(200, response.toByteArray(StandardCharsets.UTF_8).size.toLong())
                val output = exchange.responseBody

                output.write((response).toByteArray(StandardCharsets.UTF_8))
                output.close()

            }
            server.start()
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