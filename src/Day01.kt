fun main() {
    fun part1(input: List<Int>): Int {
        var count = 0
        for (i in 1 until input.size) {
            if (input[i] > input[i - 1]) count++
        }
        return count
    }

    fun part2(input: List<Int>): Int {
        var count = 0
        for (i in 3 until input.size) {
            if (input[i] > input[i - 3]) count++
        }
        return count
    }

    val testInput = listOf(
        199,
        200,
        208,
        210,
        200,
        207,
        240,
        269,
        260,
        263,
    )

    val input = readInput("Day01").map { it.toInt()}

    println(part1(testInput))
    println(part2(testInput))
    println(part1(input))
    println(part2(input))
}
