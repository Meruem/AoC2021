
data class VentsBoard(val board: List<MutableList<Int>>) {
    fun applyLine(line: Line, skipDiagonal: Boolean = false) {
        val dirx = (line.b.x - line.a.x).clamp(-1, 1)
        val diry = (line.b.y - line.a.y).clamp(-1, 1)

        if (skipDiagonal && dirx != 0 && diry != 0) return

        val move = { pos: Pos -> Pos(pos.x + dirx, pos.y + diry) }

        var pos = line.a
        while (pos != line.b) {
            board[pos.x][pos.y]++
            pos = move(pos)
        }

        board[pos.x][pos.y]++
    }

    fun computeResult() = board.flatten().count { it >= 2 }
}

fun parseLine(line: String): Line {

    fun parsePos(s: String): Pos {
        val (x, y) = s.trim().split(",", limit = 2)
        return Pos(x.trim().toInt(), y.trim().toInt())
    }

    val (left, right) = line.split("->", limit = 2)
    return Line(parsePos(left), parsePos(right))
}


fun main() {

    fun part1(board: VentsBoard, lines: List<Line>): Int {
        for (line in lines)
            board.applyLine(line, skipDiagonal = true)
        return board.computeResult()
    }

    fun part2(board: VentsBoard, lines: List<Line>): Int {
        for (line in lines)
            board.applyLine(line, skipDiagonal = false)
        return board.computeResult()
    }

    val lines = readInput("Day05").map { parseLine(it) }
    val max = maxOf(lines.maxOf { it.a.x }, lines.maxOf { it.a.y }, lines.maxOf { it.b.x }, lines.maxOf { it.b.y })
    val board1 = VentsBoard(List(max + 1) { MutableList(max + 1) { 0 } })
    val board2 = VentsBoard(List(max + 1) { MutableList(max + 1) { 0 } })

    println(part1(board1, lines))
    println(part2(board2, lines))
}
