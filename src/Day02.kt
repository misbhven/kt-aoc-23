import kotlin.math.max

fun main() {
    data class Set(val red: Int, val green: Int, val blue: Int)
    data class Game(val id: Int, val rounds: List<Set>)

    fun getGames() : List<Game> {

        //get games
        val input = readInput("Day02")

        return input.map { line ->
            //split line into the game text and the rounds
            val (game: String, rounds: String) = line.split(":")
            //get the id
            val id: Int = game.filter { it.isDigit() }.toInt()
            rounds.split(";").map { colours ->
                val red: Int = "(\\d+) red".toRegex().find(colours)?.groupValues?.get(1)?.toInt() ?: 0
                val green: Int = "(\\d+) green".toRegex().find(colours)?.groupValues?.get(1)?.toInt() ?: 0
                val blue: Int = "(\\d+) blue".toRegex().find(colours)?.groupValues?.get(1)?.toInt() ?: 0
                println("($id) r$red g$green b$blue")
                //for each round, create a set of integers that represent RGB
                Set(red, green, blue)
            }.let {
                Game(id, it)
            }
        }
    }

    fun part1(): Int {
        val maxBlue = 14
        val maxGreen = 13
        val maxRed = 12
        return getGames().filter { game ->
            game.rounds.none { cubes ->
                cubes.red > maxRed || cubes.green > maxGreen || cubes.blue > maxBlue}
        }.sumOf { it.id }
    }

    fun part2() : Int {
        return getGames().map { game ->
            var maxBlue = 0
            var maxGreen = 0
            var maxRed = 0
            game.rounds.forEach { cubes ->
                maxRed = max(maxRed, cubes.red)
                maxGreen = max(maxGreen, cubes.green)
                maxBlue = max(maxBlue, cubes.blue)
            }
            maxRed * maxGreen * maxBlue
        }.sumOf { it }
    }

    part1().println()
    part2().println()

}