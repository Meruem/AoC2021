import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

data class Pos(val x: Int, val y: Int)
data class Line(val a: Pos, val b: Pos)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("Input", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun Int.clamp(min: Int, max: Int) = this.coerceAtMost(max).coerceAtLeast(min)

fun isInRange(heightMap: List<List<Int>>, pos: Pos): Boolean =
    pos.x >= 0 && pos.x < heightMap.size && pos.y >= 0 && pos.y < heightMap[pos.x].size

fun getNeighbours(pos: Pos) =
    listOf(Pos(pos.x - 1, pos.y), Pos(pos.x + 1, pos.y), Pos(pos.x, pos.y - 1), Pos(pos.x, pos.y + 1))

fun getNeighboursDiag(pos: Pos) =
    getNeighbours(pos) + listOf(
        Pos(pos.x - 1, pos.y - 1),
        Pos(pos.x + -1, pos.y + 1),
        Pos(pos.x + 1, pos.y - 1),
        Pos(pos.x + 1, pos.y + 1)
    )

fun <T> List<T>.splitByElement(elem: T): Pair<List<T>, List<T>> {
    val elemPos = this.indexOf(elem)
    val first = this.subList(0, elemPos)
    val rest = this.subList(elemPos + 1, this.size)
    return Pair(first, rest)
}
