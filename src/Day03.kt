fun main() {

    fun getMostCommonBit(lines: List<String>): List<Int> {
        val counter = MutableList(lines[0].length) { 0 }
        for (line in lines) {
            for (i in line.indices) {
                when (line[i]) {
                    '0' -> counter[i]--
                    '1' -> counter[i]++
                }
            }
        }
        return counter
    }

    fun part1(input: List<String>): Int {
        val counter = getMostCommonBit(input)
        var gamma = ""
        var epsilon = ""
        for (i in counter) {
            gamma += if (i >= 0) "1" else "0"
            epsilon += if (i >= 0) "0" else "1"
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun filterLines(input: List<String>, bitCheck: (Int) -> Char): String? {
        var filtered = input
        var pos = 0

        while (filtered.size > 1 && pos < input[0].length) {
            val counter = getMostCommonBit(filtered)
            val bit = bitCheck(counter[pos])
            filtered = filtered.filter { it[pos] == bit }
            pos++
        }

        return if (filtered.size > 1) null else filtered[0]
    }


    fun part2(input: List<String>): Int {

        val oxygen = filterLines(input) { if (it >= 0) '1' else '0' }
        val co2 = filterLines(input) { if (it >= 0) '0' else '1' }

        return oxygen!!.toInt(2) * co2!!.toInt(2)
    }

    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))
}
