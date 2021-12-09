fun main() {

    fun isInRange(heightMap: List<List<Int>>, x: Int, y: Int): Boolean =
        x >= 0 && x < heightMap.size && y >= 0 && y < heightMap[x].size

    fun getNeighbours(i: Int, j: Int) = listOf(Pair(i - 1, j), Pair(i + 1, j), Pair(i, j - 1), Pair(i, j + 1))

    fun getLowPoints(heightMap: List<List<Int>>): List<Pair<Int, Int>> {
        val res = mutableListOf<Pair<Int, Int>>()
        for (i in heightMap.indices)
            for (j in heightMap[i].indices) {

                fun checkIsLower(x: Int, y: Int) =
                    !isInRange(heightMap, x, y) || heightMap[i][j] < heightMap[x][y]

                if (getNeighbours(i, j).all { checkIsLower(it.first, it.second) }) res.add(Pair(i, j))
            }

        return res.toList()
    }

    fun part1(heightMap: List<List<Int>>): Int {
        return getLowPoints(heightMap).sumOf { (x, y) -> heightMap[x][y] + 1 }
    }

    fun getBasinSize(heightMap: List<List<Int>>, x: Int, y: Int): Int {
        val visited = mutableSetOf(Pair(x, y))
        val toVisit = mutableListOf(Pair(x, y))
        var size = 0
        while (toVisit.isNotEmpty()) {
            size++
            val item = toVisit[0]
            val (x, y) = item
            toVisit.removeAt(0)
            val newNeighbours =
                getNeighbours(x, y).filter {
                    isInRange(
                        heightMap,
                        it.first,
                        it.second
                    ) && heightMap[it.first][it.second] < 9 && !visited.contains(it)
                }
            toVisit.addAll(newNeighbours)
            visited.addAll(newNeighbours)
        }

        return size
    }

    fun part2(heightMap: List<List<Int>>): Int {
        val lowPoints = getLowPoints(heightMap)
        val sizes = lowPoints.map { getBasinSize(heightMap, it.first, it.second) }.sortedDescending()
        return sizes[0] * sizes[1] * sizes[2]
    }

    val input = readInput("Day09")

    val heightMap = input.map { line -> line.map { c -> c.code - '0'.code } }

    println(part1(heightMap))
    println(part2(heightMap))
}
