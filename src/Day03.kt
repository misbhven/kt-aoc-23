fun main() {

    fun parseInput() : List<Item> {
        val input = readInput("Day03")
        val items: List<Item> = mutableListOf()
        input.forEachIndexed { y, string ->
            var startingX = -1
            var tempNumber = ""
            string.forEachIndexed { x, char ->
                if (char.isDigit()) {
                    tempNumber += char
                    if (startingX == -1) startingX = x
                }
                else {
                    if (char != '.') {
                        items.addLast(Symbol(y, x, char.toString()))
                    }
                    if(tempNumber.isNotEmpty()) {
                        items.addLast(Number(y = y, xStart = startingX, xEnd = (x - 1), value = tempNumber))
                        tempNumber = ""
                        startingX = -1
                    }
                }
            }
            if (tempNumber.isNotEmpty()) {
                items.addLast(Number(y = y, xStart = startingX, xEnd = (string.length), value = tempNumber))
            }
        }
        return items
    }

    fun part1(list: List<Item>): Int {
        val numberSet: MutableSet<Number> = list.filterIsInstance<Number>().toMutableSet()
        val symbolSet: MutableSet<Symbol> = list.filterIsInstance<Symbol>().toMutableSet()
        return numberSet.filter { number ->
            symbolSet.any { symbol ->
                symbol.x in number.windowX && symbol.y in number.windowY
            }
        }.sumOf { it.value }
    }

    fun part2(list: List<Item>): Int {
        val numberSet: MutableSet<Number> = list.filterIsInstance<Number>().toMutableSet()
        val symbolSet: MutableSet<Symbol> = list.filterIsInstance<Symbol>().filter { it.value == "*" }.toMutableSet()

        return symbolSet.map { symbol ->
            numberSet.filter { symbol.x in it.windowX && symbol.y in it.windowY }
        }.filter { possibleNumbers ->
            possibleNumbers.size == 2
        }.map { numberPair ->
            numberPair.first().value to numberPair.last().value
        }.sumOf { pairs ->
            pairs.first * pairs.second
        }
    }

    part1(parseInput()).println() //556057
    part2(parseInput()).println() //82824352
}
sealed class Item
data class Number(val y: Int, val x: IntRange, val value: Int) : Item() {
    val windowX : IntRange = x.first - 1..x.last + 1
    val windowY : IntRange = y - 1..y + 1
}

fun Number(y: Int, xStart: Int, xEnd: Int, value: String) : Number = Number(y, xStart..xEnd, value.toInt())

data class Symbol(val y: Int, val x: Int, val value: String) : Item()