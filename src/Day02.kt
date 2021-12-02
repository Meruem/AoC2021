enum class Direction {
    FORWARD,
    UP,
    DOWN
}

data class Command(val direction: Direction, val value: Int)

fun main() {
    fun part1(input: List<Command>): Int {
        var horPos = 0
        var depth = 0
        for (cmd in input) {
            when (cmd.direction) {
                Direction.FORWARD -> horPos += cmd.value
                Direction.DOWN -> depth += cmd.value
                Direction.UP -> depth -= cmd.value
            }
        }
        return horPos * depth
    }

    fun part2(input: List<Command>): Int {
        var horPos = 0
        var depth = 0
        var aim = 0
        for (cmd in input) {
            when (cmd.direction) {
                Direction.FORWARD -> {
                    horPos += cmd.value
                    depth += aim * cmd.value
                }
                Direction.DOWN -> aim += cmd.value
                Direction.UP -> aim -= cmd.value
            }
        }
        return horPos * depth
    }

    fun parseInputLine(line: String): Command {
        val (dir, v) = line.split(" ", limit = 2)
        return Command(Direction.valueOf(dir.uppercase()), v.toInt())
    }

    val input = readInput("Day02").map { parseInputLine(it) }

    println(part1(input))
    println(part2(input))
}
