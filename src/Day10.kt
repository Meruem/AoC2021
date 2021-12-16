sealed class CheckResult() {
    data class Corrupted(val c: Char) : CheckResult()
    data class Incomplete(val chunks: List<Char>) : CheckResult()
}

fun main() {

    fun matchingClosingChar(c: Char) = when (c) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> c
    }

    fun checkLine(line: String): CheckResult {
        val chunks = mutableListOf<Char>()
        for (c in line) {
            when (c) {
                '{', '[', '(', '<' -> chunks.add(c)
                '}', ']', ')', '>' -> {
                    if (chunks.isEmpty() || matchingClosingChar(chunks.last()) != c) {
                        return CheckResult.Corrupted(c)
                    }
                    chunks.removeLast()
                }
            }
        }

        return CheckResult.Incomplete(chunks)
    }

    fun part1(input: List<String>): Int {

        fun getScore(c: Char): Int = when (c) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }

        return input.sumOf {
            when (val res = checkLine(it)) {
                is CheckResult.Corrupted -> getScore(res.c)
                else -> 0
            }
        }
    }

    fun part2(input: List<String>): Long {
        fun getScore(chunks: List<Char>) =
            chunks.fold(0L) { acc, c ->
                val score = when (c) {
                    '(' -> 1L
                    '[' -> 2L
                    '{' -> 3L
                    '<' -> 4L
                    else -> 0L
                }
                acc * 5 + score
            }

        val result = input
            .map { checkLine(it) }
            .filterIsInstance<CheckResult.Incomplete>()
            .map {
                getScore(it.chunks.reversed())
            }.sorted()

        return result[(result.size -1) / 2]
    }

    val input = readInput("Day10")

    println(part1(input))
    println(part2(input))
}
