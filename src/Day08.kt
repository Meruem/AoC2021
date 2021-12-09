typealias Display = Set<Char>

data class LineInfo(val def: List<Display>, val outputs: List<Display>)

fun main() {

    fun part1(input: List<LineInfo>): Int {
        return input.sumOf { info ->
            info.outputs.count { it.size in listOf(2, 3, 4, 7) }
        }
    }


    fun part2(input: List<LineInfo>): Int {

        val numbers = mapOf(
            setOf('a', 'b', 'c', 'e', 'f', 'g') to "0",
            setOf('c', 'f') to "1",
            setOf('a', 'c', 'd', 'e', 'g') to "2",
            setOf('a', 'c', 'd', 'f', 'g') to "3",
            setOf('b', 'c', 'd', 'f') to "4",
            setOf('a', 'b', 'd', 'f', 'g') to "5",
            setOf('a', 'b', 'd', 'e', 'f', 'g') to "6",
            setOf('a', 'c', 'f') to "7",
            setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') to "8",
            setOf('a', 'b', 'c', 'd', 'f', 'g') to "9",
        )

        fun isValidSet(displays: List<Display>) =
            displays.size == numbers.size &&
                    displays.all { numbers.keys.contains(it) }


        val chars = ('a'..'g').toList()
        val translateMap = chars.associateWith { chars }

        fun Display.applyTranslateMap(translateMap: Map<Char, List<Char>>) =
            this.map { d -> translateMap[d]!![0] }.toSet()


        fun solve(translateMap: Map<Char, List<Char>>, defs: List<Display>): Map<Char, List<Char>>? {
            if (translateMap.values.all { it.count() == 1 }) {
                return if (isValidSet(defs.map { it.applyTranslateMap(translateMap) })) translateMap else null
            }

            for (ch in chars) {
                if (translateMap[ch]!!.size > 1) {
                    for (target in translateMap[ch]!!) {
                        val newTranslateMap =
                            translateMap.map { entry -> entry.key to if (entry.key == ch) listOf(target) else entry.value.filter { it != target } }
                                .toMap()
                        val result = solve(newTranslateMap, defs)
                        if (result != null) return result
                    }
                }
            }

            return null
        }

        fun trimSingles(translateMap: Map<Char, List<Char>>, defs: List<Display>): Map<Char, List<Char>> {
            val singles = mapOf(
                defs.first { it.size == 4 } to setOf('b', 'c', 'd', 'f'),
                defs.first { it.size == 2 } to setOf('c', 'f'),
                defs.first { it.size == 3 }.minus(defs.first { it.size == 2 }) to setOf('a')
            )

            val newTranslateMap = translateMap.toMutableMap()
            for (single in singles) {
                for (c in single.key)
                    newTranslateMap[c] = single.value.toList()
            }

            return newTranslateMap.toMap()

        }

        fun getOutput(lineInfo: LineInfo): Int {
            val trimmedTranslateMap = trimSingles(translateMap, lineInfo.def)
            val t = solve(trimmedTranslateMap, lineInfo.def) ?: return 0
            val output = lineInfo.outputs.map { numbers[it.applyTranslateMap(t)]!! }.joinToString(separator = "") { it }
            return output.toInt()
        }

        return input.sumOf { getOutput(it) }
    }

    fun parseSignal(signal: String): Display =
        HashSet<Char>(signal.toCharArray().asList())

    fun createInfo(displays: List<List<Display>>) = LineInfo(displays[0], displays[1])


    fun parseLine(line: String) = createInfo(line
        .split("|", limit = 2)
        .map { ln -> ln.trim().split(" ").map { parseSignal(it) } })

    val input = readInput("Day08").map { parseLine(it) }


    println(part1(input))
    println(part2(input))
}
