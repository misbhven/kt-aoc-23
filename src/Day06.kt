fun main() {

    val test = false

    fun part1() : Int {
        val input = if (test) readInput("Day06_test") else readInput("Day06")
        val times = input[0].split(" ").map { it.filter { it.isDigit() } }.filter { it.isNotEmpty() }.map {it.toInt()}
        val distances = input[1].split(" ").map { it.filter { it.isDigit() } }.filter { it.isNotEmpty() }.map { it.toInt() }
        println("$times\n$distances")

        return times.mapIndexed { index, time ->
            var count : Int = 0
            for (second in 0..time) {
                val distanceTravelled = second * (time - second)
                if (distanceTravelled > distances[index]) count++
            }
            count
        }.reduce { acc, count -> acc * count }
    }

    fun part2() : Int {
        val input = if (test) readInput("Day06_test") else readInput("Day06")
        val time = input[0].split(" ").map { it.filter { it.isDigit() } }.filter { it.isNotEmpty() }.joinToString("").toLong()
        val distance = input[1].split(" ").map { it.filter { it.isDigit() } }.filter { it.isNotEmpty() }.joinToString("").toLong()

        var count : Int = 0
        for (second: Long in 0 .. time) {
            val tempDist : Long = second * (time - second)
            if (tempDist > distance) count++
        }

        return count

    }
    //distance travelled = holdTime * (totalTime - holdTime)

    println(part1())
    println(part2())
}