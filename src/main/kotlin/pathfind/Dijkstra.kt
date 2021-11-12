package pathfind

import graph.Graph

/**
 * graph Graph: A graph formatted as adjacency list.
 * startNode Int: The index of the starting node.
 */
class Dijkstra(val graph: Graph, val startNode : Int){
    val processedNodes: MutableSet<Int> = mutableSetOf()

    /*Funktion Dijkstra(Graph, Startknoten):
          initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q)
          solange Q nicht leer:                       // Der eigentliche Algorithmus
              u:= Knoten in Q mit kleinstem Wert in abstand[]
              entferne u aus Q                                // für u ist der kürzeste Weg nun bestimmt
              für jeden Nachbarn v von u:
                  falls v in Q:                            // falls noch nicht berechnet
                     distanz_update(u,v,abstand[],vorgänger[])   // prüfe Abstand vom Startknoten zu v
          return vorgänger[]



    Methode initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q):
          für jeden Knoten v in Graph:
              abstand[v]:= unendlich
              vorgänger[v]:= null
          abstand[Startknoten]:= 0
          Q:= Die Menge aller Knoten in Graph



    Methode distanz_update(u,v,abstand[],vorgänger[]):
          alternativ:= abstand[u] + abstand_zwischen(u, v)   // Weglänge vom Startknoten nach v über u
          falls alternativ < abstand[v]:
              abstand[v]:= alternativ
              vorgänger[v]:= u
    */

    // track used nodes
    // track reachable nodes -> sort in Heap
    // track predecessors
    // track length
    //
}