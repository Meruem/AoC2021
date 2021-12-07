data class Position(val number: Int, var checked: Boolean)

data class Board(val board: List<List<Position>>) {
    fun checkRow(rowNumber: Int) = board[rowNumber].all { it.checked }
    fun checkColumn(columnNumber: Int) = board.all { it[columnNumber].checked }
    fun crossNumber(number: Int): Boolean {
        for (row in board.indices)
            for (column in board[row].indices) {
                if (board[row][column].number == number) {
                    board[row][column].checked = true
                    return checkRow(row) || checkColumn(column)
                }
            }

        return false
    }

    fun computeResult(number: Int) = board.flatten().filter { !it.checked }.sumOf { it.number } * number
}

fun create(numbers: List<List<Int>>): Board {
    return Board(numbers.map { row -> row.map { col -> Position(col, false) } })
}

fun createBoards(input: List<String>): List<Board> {
    val boards = mutableListOf<Board>()
    var pos = 2
    while (pos < input.size) {
        val board = create(input.subList(pos, pos + 5).map { row -> row.chunked(3).map { it.trim().toInt() } })
        boards.add(board)
        pos += 6
    }
    return boards.toList()
}


fun main() {

    fun part1(boards: List<Board>, numbers: List<Int>): Int {
        for (number in numbers) {
            for (board in boards) {
                if (board.crossNumber(number)) return board.computeResult(number)
            }
        }

        return 0
    }

    fun part2(boards: List<Board>, numbers: List<Int>): Int {
        var remainingBoards = boards
        var lastBoard: Board? = null
        for (number in numbers) {
            remainingBoards = remainingBoards.filter { !it.crossNumber(number) }

            if (remainingBoards.size == 1)
                lastBoard = remainingBoards[0]

            if (remainingBoards.isEmpty())
                return lastBoard!!.computeResult(number)
        }

        return 0
    }

    val input = readInput("Day04")
    val numbers = input[0].split(",").map { it.toInt() }

    val boards1 = createBoards(input)
    val boards2 = createBoards(input)

    println(part1(boards1, numbers))
    println(part2(boards2, numbers))
}
