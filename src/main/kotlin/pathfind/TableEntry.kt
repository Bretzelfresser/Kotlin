package pathfind

import graph.Node

class TableEntry(val node : Int, val predecessor : Int, val weight : Double) : Comparable<TableEntry>{
    override fun compareTo(other: TableEntry): Int {
        if(this.weight < other.weight)
            return -1
        if (this.weight > other.weight)
            return 1
        return 0
    }
}