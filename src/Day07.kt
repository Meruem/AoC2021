fun main() {

    fun calculateMinFuel(input: List<Int>, fuel: (Int) -> Int): Int {
        val min = input.minOf { it }
        val max = input.maxOf { it }

        return (min..max).map { i -> input.sumOf { fuel(kotlin.math.abs(i - it)) } }.minOf { it }
    }

    fun part1(input: List<Int>) = calculateMinFuel(input) { it }
    fun part2(input: List<Int>) = calculateMinFuel(input) {
        it * (it + 1) / 2
    }

    val input = readInput("Day07")
    val positions = input[0].split(",").map { it.toInt() }

    println(part1(positions))
    println(part2(positions))
}
