package pathfind

data class TableEntry(var node : Int, var preDecessor : Int, var neededWeight : Int) : Comparable<TableEntry> {
    override fun compareTo(other: TableEntry): Int {
        var comp = neededWeight.compareTo(other.neededWeight)
        if(comp == 0){
            return node.compareTo(other.node)
        }
        return comp
    }
}