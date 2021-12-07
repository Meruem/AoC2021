fun main() {
    fun iterate(fish: MutableList<Int>) {
        for (i in fish.indices) {
            if (fish[i] == 0) {
                fish[i] = 6
                fish.add(8)
            }
            else fish[i]--
        }
    }

    fun part1(input: List<Int>): Int {
        val fish = input.toMutableList()
        for (i in 1..80) iterate(fish)

        return fish.size
    }

    fun part2(input: List<Int>): Long {
        val buckets = (0..8).associateWith { 0L }.toMutableMap()
        for (fish in input) buckets[fish] = buckets[fish]!! + 1
        for (i in 1..256) {
            val newFish = buckets[0]
            for (b in 0..7) buckets[b] = buckets[b + 1]!!
            buckets[8] = newFish!!
            buckets[6] = buckets[6]!! + newFish!!
        }

        return buckets.values.sum()
    }


    val input = readInput("Day06")
    val fish = input[0].split(",").map { it.toInt() }.toMutableList()


    println(part1(fish))
    println(part2(fish))
}
