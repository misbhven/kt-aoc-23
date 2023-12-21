import java.lang.Math.pow
import kotlin.math.pow
fun main() {

    val test = false

    fun part1() : Int {
        val input = if (test) readInput("Day04_test") else readInput("Day04")
        return input.map { line ->
            val (idStr: String, winningNumsStr: String, playingNumsStr: String) = line.split(":", "|")
            val id: Int = "(\\d+)".toRegex().find(idStr)?.groupValues?.get(1)?.toInt() ?: 0
            val winningNums : List<Int> = winningNumsStr.split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
            val playingNums : List<Int> = playingNumsStr.split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
            val matches : List<Int> = playingNums.filter { winningNums.contains(it) }
            val points = if (matches.isNotEmpty()) 2.0.pow(matches.size-1).toInt() else 0
            Game(id, winningNums, playingNums, points)
        }.sumOf {it.points}
    }

    fun part2() : Int {
        val input = if (test) readInput("Day04_test") else readInput("Day04")
        return input.map { line ->
            val (idStr: String, winningNumsStr: String, playingNumsStr: String) = line.split(":", "|")
            val id: Int = "(\\d+)".toRegex().find(idStr)?.groupValues?.get(1)?.toInt() ?: 0
            val winningNums : List<Int> = winningNumsStr.split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
            val playingNums : List<Int> = playingNumsStr.split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
            val matches : Int = winningNums.count { it in playingNums }
            Game(id, winningNums, playingNums, matches)
        }.let { games ->
            val cardCount = IntArray(games.size) { 1 }
            games.map {game ->
                (1..game.points).forEach {
                     cardCount[game.id + it] += cardCount[game.id]
                }
            }
            cardCount
        }.sum()
    }

    println(part1())
    println(part2())
}


data class Game(val id: Int, val winningNums: List<Int>, val playingNums: List<Int>, val points: Int)