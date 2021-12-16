fun main() {

    fun getLowPoints(heightMap: List<List<Int>>): List<Pos> {
        val res = mutableListOf<Pos>()
        for (i in heightMap.indices)
            for (j in heightMap[i].indices) {

                fun checkIsLower(pos: Pos) =
                    !isInRange(heightMap, pos) || heightMap[i][j] < heightMap[pos.x][pos.y]

                if (getNeighbours(Pos(i, j)).all { checkIsLower(it) }) res.add(Pos(i, j))
            }

        return res.toList()
    }

    fun part1(heightMap: List<List<Int>>): Int {
        return getLowPoints(heightMap).sumOf { (x, y) -> heightMap[x][y] + 1 }
    }

    fun getBasinSize(heightMap: List<List<Int>>, basin: Pos): Int {
        val visited = mutableSetOf(basin)
        val toVisit = mutableListOf(basin)
        var size = 0
        while (toVisit.isNotEmpty()) {
            size++
            val item = toVisit[0]
            toVisit.removeAt(0)
            val newNeighbours =
                getNeighbours(item).filter {
                    isInRange(
                        heightMap,
                        it
                    ) && heightMap[it.x][it.y] < 9 && !visited.contains(it)
                }
            toVisit.addAll(newNeighbours)
            visited.addAll(newNeighbours)
        }

        return size
    }

    fun part2(heightMap: List<List<Int>>): Int {
        val lowPoints = getLowPoints(heightMap)
        val sizes = lowPoints.map { getBasinSize(heightMap, it) }.sortedDescending()
        return sizes[0] * sizes[1] * sizes[2]
    }

    val input = readInput("Day09")

    val heightMap = input.map { line -> line.map { c -> c.code - '0'.code } }

    println(part1(heightMap))
    println(part2(heightMap))
}
