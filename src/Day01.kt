fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val digits = line.filter{ it.isDigit() }
            if (digits.isNotEmpty()){
                total += "${digits.first()}${digits.last()}".toInt()
            }
        }
        return total
    }

    fun part2(input: List<String>) : Int {

        val dict = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
        val possibleValues = dict.keys + dict.values.map { it.toString() }

        var total = 0
        for (line in input) {
            val firstNumber = line.findAnyOf(possibleValues, 0,true)?.second.toString()
            val secondNumber = line.findLastAnyOf(possibleValues, line.length,true)?.second.toString()

            val first: String = if(firstNumber.length > 1) dict[firstNumber].toString() else firstNumber
            val second: String = if (secondNumber.length > 1) dict[secondNumber].toString() else secondNumber

            total += "$first$second".toInt()
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
