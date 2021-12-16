fun main() {

    val start = "start"
    val end = "end"

    fun getNextItems(links: List<Pair<String, String>>, item: String): List<String> {
        val result = mutableListOf<String>()
        for (link in links) {
            if (link.first == item) result.add(link.second)
            if (link.second == item) result.add(link.first)
        }

        return result
    }

    fun <T> traverse(start: T, getNextItems: (List<T>) -> List<List<T>>): Set<List<T>> {
        val visited = mutableSetOf(listOf(start))
        val queue = mutableListOf(listOf(start))
        while (queue.isNotEmpty()) {
            val current = queue[0]
            queue.removeAt(0)
            val nextItems = getNextItems(current).filter { !visited.contains(it) }
            queue.addAll(nextItems)
            visited.addAll(nextItems)
        }

        return visited
    }

    fun part1(input: List<Pair<String, String>>) =
        traverse(start) { current ->
            getNextItems(input, current.last())
                .filter { it[0].isUpperCase() || !current.contains(it) }
                .map { current + it }
        }.count { it.last() == end }

    fun lowerCaseDoubles(path: List<String>) =
        path.filter { it[0].isLowerCase() }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }


    fun part2(input: List<Pair<String, String>>) =
        traverse(start) { current ->
            getNextItems(input, current.last())
                .filter { it != start && (!current.contains(end)) && (it[0].isUpperCase() || current.count { c -> c == it } <= 1) }
                .map { current + it }
                .filter { lowerCaseDoubles(it) <= 1 }
        }.count { it.last() == end }

    fun parseLine(line: String): Pair<String, String> {
        val (a, b) = line.split("-", limit = 2)
        return Pair(a, b)
    }

    val input = readInput("Day12").map { parseLine(it) }

    println(part1(input))
    println(part2(input))
}
