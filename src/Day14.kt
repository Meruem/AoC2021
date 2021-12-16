fun main() {
    data class Insertion(val pair: Pair<Char, Char>, val insert: Char)
    data class Input(val template: String, val insertions: List<Insertion>)

    fun iterate(template: List<Char>, insertions: Map<Pair<Char, Char>, Char>): List<Char> {
        val result = mutableListOf(template[0])
        for (i in 0..template.size - 2) {
            val insertion = insertions.getOrDefault(Pair(template[i], template[i + 1]), null)
            if (insertion != null) {
                result.add(insertion)
            }
            result.add(template[i + 1])
        }
        return result
    }

    fun part1(input: Input): Int {
        val insertionMap = input.insertions.associate { Pair(it.pair, it.insert) }
        val result = (1..10).fold(input.template.toCharArray().toList()) { acc, _ -> iterate(acc, insertionMap) }
        val resultMap = result.groupingBy { it }.eachCount().entries.sortedBy { it.value }
        return resultMap.last().value - resultMap.first().value
    }

    fun MutableMap<Pair<Char, Char>, Long>.inc(pair: Pair<Char, Char>, n: Long = 1) {
        if (this.containsKey(pair))
            this[pair] = this[pair]!! + n
        else this[pair] = n
    }

    fun part2(input: Input): Long {
        val insertionMap = input.insertions.associate { Pair(it.pair, it.insert) }

        val usedChars = input.insertions.flatMap { listOf(it.pair.first, it.pair.second, it.insert) }.distinct()
        val allCombinations = usedChars.flatMap { a -> usedChars.map { b -> Pair(a, b) } }.associateWith { 0L }
        val initial = allCombinations.toMutableMap()
        for (i in 0..input.template.length - 2) {
            initial.inc(Pair(input.template[i], input.template[i + 1]))
        }

        fun iterate2(pairs: Map<Pair<Char, Char>, Long>): Map<Pair<Char, Char>, Long> {
            val newMap = mutableMapOf<Pair<Char, Char>, Long>()
            for (pair in pairs.filter { it.value > 0 }) {
                val insertion = insertionMap.getOrDefault(pair.key, null)
                if (insertion != null) {
                    newMap.inc(Pair(pair.key.first, insertion), pair.value)
                    newMap.inc(Pair(insertion, pair.key.second), pair.value)
                } else {
                    newMap.inc(pair.key, pair.value)
                }
            }

            return newMap
        }


        val result = (1..40).fold(initial.toMap()) { acc, _ -> iterate2(acc) }

        val counts = mutableMapOf<Char, Long>()
        for (pair in result) {
            counts[pair.key.first] = counts.getOrDefault(pair.key.first, 0L) + pair.value
            counts[pair.key.second] = counts.getOrDefault(pair.key.second, 0L) + pair.value
        }

        for (count in counts) {
            counts[count.key] = if (count.value % 2 == 0L) count.value / 2 else (count.value + 1) / 2
        }

        val resultMap = counts.entries.sortedBy { it.value }
        return resultMap.last().value - resultMap.first().value
    }


    fun parseLines(lines: List<String>): Input {
        val (template, insertLines) = lines.splitByElement("")
        return Input(template.first(), insertLines.map { line ->
            val (left, right) = line.split(" -> ", limit = 2)
            Insertion(Pair(left[0], left[1]), right[0])
        })
    }


    val input = readInput("Day14")
    val parsedInput = parseLines(input)

    println(part1(parsedInput))
    println(part2(parsedInput))
}
