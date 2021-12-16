data class Fold(val axis: String, val value: Int)

class DotGrid {
    var positions = mutableSetOf<Pos>()
    fun fold(fold: Fold) {
        val newPositions = mutableSetOf<Pos>()
        for (pos in positions) {
            if (pos.y > fold.value && fold.axis == "y") {
                val newPos = Pos(pos.x, fold.value - (pos.y - fold.value))
                newPositions.add(newPos)
            } else if (pos.x > fold.value && fold.axis == "x") {
                val newPos = Pos(fold.value - (pos.x - fold.value), pos.y)
                newPositions.add(newPos)
            } else {
                newPositions.add(pos)
            }
        }
        positions = newPositions
    }
}

fun main() {

    data class Input(val grid: DotGrid, val folds: List<Fold>)

    fun part1(input: Input): Int {
        input.grid.fold(input.folds.first())
        return input.grid.positions.size
    }

    fun part2(input: Input) {
        for (fold in input.folds) input.grid.fold(fold)
        val maxX = input.grid.positions.maxOf { it.x }
        val maxY = input.grid.positions.maxOf { it.y }
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                print(if (input.grid.positions.contains(Pos(x, y))) "#" else ".")

            }
            println()
        }
    }

    fun parseLines(lines: List<String>): Input {

        fun parsePos(line: String): Pos {
            val (x, y) = line.split(",", limit = 2)
            return Pos(x.trim().toInt(), y.trim().toInt())
        }

        fun parseFold(line: String): Fold {
            val (axis, value) = line.removePrefix("fold along ").split("=", limit = 2)
            return Fold(axis = axis, value = value.trim().toInt())
        }

        val (posInfo, foldInfo) = lines.splitByElement("")

        val positions = posInfo.map { parsePos(it) }.toMutableSet()
        val folds = foldInfo.map { parseFold(it) }.toList()

        return Input(DotGrid().apply { this.positions = positions }, folds)
    }


    val input = readInput("Day13")
    val parsedInput = parseLines(input)
    val parsedInput2 = parseLines(input)

    println(part1(parsedInput))
    part2(parsedInput2)

}
