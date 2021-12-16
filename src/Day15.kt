fun main() {

    fun traverse(riskMap: List<List<Int>>): MutableList<MutableList<Int>> {
        val pathMap = riskMap.map { line -> line.map { Int.MAX_VALUE }.toMutableList() }.toMutableList()
        val queue = mutableListOf(Pos(0, 0))

        pathMap[0][0] = 0
        while (queue.isNotEmpty()) {
            val item = queue[0]
            queue.removeAt(0)
            val currentTotal = pathMap[item.x][item.y]
            getNeighbours(item)
                .filter { isInRange(riskMap, it) }
                .forEach { n ->
                    val newTotal = currentTotal + riskMap[n.x][n.y]
                    if (newTotal < pathMap[n.x][n.y]) {
                        pathMap[n.x][n.y] = newTotal
                        queue.add(n)
                    }
                }
        }

        return pathMap
    }

    fun part1(input: List<String>): Int {
        val riskMap = input.map { line -> line.map { c -> c.code - '0'.code } }
        val pathMap = traverse(riskMap)
        return pathMap.last().last()
    }

    fun part2(input: List<String>): Int {
        fun Int.inc(n: Int) = if (this + n > 9) this + n - 9 else this + n

        val riskMapRight = input.map { line -> (0..4).flatMap { i -> line.map { c -> (c.code - '0'.code).inc(i) } } }
        val riskMap = (0..4).flatMap { i -> riskMapRight.map { line -> line.map { n -> n.inc(i) } } }

        val pathMap = traverse(riskMap)
        return pathMap.last().last()
    }


    val input = readInput("Day15")

    println(part1(input))
    println(part2(input))
}
