typealias Grid = MutableList<MutableList<Int>>

fun Grid.at(pos: Pos) = this[pos.x][pos.y]

fun main() {

    fun step(grid: Grid): Pair<Grid, Int> {
        val flashed = mutableSetOf<Pos>()

        fun flashPosition(pos: Pos) {
            val queue = mutableListOf(pos)
            while (queue.isNotEmpty()) {
                val current = queue[0]
                queue.removeAt(0)

                if (flashed.contains(current)) continue

                if (grid.at(current) < 9) grid[current.x][current.y] = grid.at(current) + 1
                else {
                    flashed.add(current)
                    val flashedN = getNeighboursDiag(current).filter { isInRange(grid, it) && !flashed.contains(it) }
                    queue.addAll(flashedN)
                }
            }
        }

        for (i in grid.indices)
            for (j in grid[i].indices) {
                val pos = Pos(i, j)
                if (flashed.contains(pos)) continue

                flashPosition(pos)
            }

        for (pos in flashed) grid[pos.x][pos.y] = 0

        return Pair(grid, flashed.size)
    }

    fun part1(octopus: List<List<Int>>): Int {
        var grid = octopus.map { it.toMutableList() }.toMutableList()
        var result = 0
        for (i in 1..100) {
            val (newGrid, newFlashes) = step(grid)
            result += newFlashes
            grid = newGrid
        }

        return result
    }

    fun part2(octopus: List<List<Int>>): Int {
        var grid = octopus.map { it.toMutableList() }.toMutableList()
        var fleshes = 0
        var steps = 0
        val gridSize = octopus.sumOf { it.size }
        while(fleshes != gridSize) {
            steps++
            val (newGrid, newFlashes) = step(grid)
            grid = newGrid
            fleshes = newFlashes
        }

        return steps
    }

    val input = readInput("Day11").map { line -> line.map { it.code - '0'.code } }

    println(part1(input))
    println(part2(input))
}
