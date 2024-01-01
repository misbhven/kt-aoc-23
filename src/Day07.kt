fun main() {

    val cardMap : Map<Char, Int> = mapOf('2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9, 'T' to 10, 'J' to 11, 'Q' to 12, 'K' to 13, 'A' to 14)
    val cardMapJoker : Map<Char, Int> = mapOf('2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9, 'T' to 10, 'J' to 1, 'Q' to 12, 'K' to 13, 'A' to 14)
    val test = false

    fun part1() : Int {
        val input = if (test) readInput("Day07_test") else readInput("Day07")
        return input.map {
            val (handStr : String, betStr: String) = it.split(" ")
            val cards = handStr.map { cardMap[it] }
            val cardCounts = cards.groupingBy { it }.eachCount().values.sortedDescending()

            Hand(cards = cards, cardCounts = cardCounts, bet = betStr.toInt(), score = scoreHand(cardCounts) )
        }.sortedDescending().mapIndexed { index, hand ->
            hand.bet * (index +1) }.sum()
    }

    fun part2() : Int {
        val input = if (test) readInput("Day07_test") else readInput("Day07")
        return input.map {
            val (handStr : String, betStr: String) = it.split(" ")
            val cards = handStr.map { cardMapJoker[it] }

            val cardCounts = cards.filter { it!! > 1 }.groupingBy { it }.eachCount().values.sortedDescending().toMutableList()

            if (cardCounts.isNotEmpty()) cardCounts[0] += cards.count { it == 1 } else cardCounts.add(5)
            Hand(cards = cards, cardCounts = cardCounts, bet = betStr.toInt(), score = scoreHand(cardCounts) )
        }.sortedDescending().mapIndexed { index, hand ->
            hand.bet * (index +1) }.sum()
    }
    println(part1())
    println(part2())
}

fun scoreHand(cardCounts: List<Int>) : Int {
    var score = 0
    when (cardCounts[0]) {
        2 -> {
            score = if (cardCounts.size > 3) Scores.PAIR.score else Scores.TWOPAIR.score
        }
        3 -> {
            score = if (cardCounts.size > 2) Scores.THREEKIND.score else Scores.FULLHOUSE.score
        }
        4 -> score = Scores.FOURKIND.score
        5 -> score = Scores.FIVEKIND.score
    }
    return score
}

data class Hand(val cards : List<Int?>, val cardCounts : List<Int>, val bet : Int, var score : Int) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int = when {
        this.score != other.score -> other.score compareTo this.score
        else -> {
            var res = 0
            for (card in 0..this.cards.size) {
                if (this.cards[card]!! > other.cards[card]!!) {
                    res = -1
                    break
                }
                if (this.cards[card]!! < other.cards[card]!!) {
                    res = 1
                    break
                }
            }
            res
        }
    }
}

enum class Scores(val score: Int) {
    FIVEKIND(6),
    FOURKIND(5),
    FULLHOUSE(4),
    THREEKIND(3),
    TWOPAIR(2),
    PAIR(1),
}