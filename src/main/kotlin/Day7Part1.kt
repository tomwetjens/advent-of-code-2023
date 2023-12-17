import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val ranked = Path("inputs/day7.txt")
        .readLines()
        .map(Day7Part1::parseLine)
        .sortedBy { it.first }

    ranked.forEach { (hand, bid) -> println("$hand $bid") }

    // part 1
    val total = ranked.mapIndexed { rank, bid -> bid.second * (rank + 1) }.sum()
    println(total)
}

object Day7Part1 {

    val CARD_VALUES_LOW_TO_HIGH =
        arrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversedArray()

    fun parseLine(line: String): Pair<Hand, Int> {
        val split = line.split(' ')
        val bid = split[1].toInt()
        return Hand(split[0].toCharArray().toList()) to bid
    }

    enum class HandType : Comparable<HandType> {
        HIGH_CARD,
        PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND,
    }

    data class Hand(val cards: List<Char>) : Comparable<Hand> {

        private val cardValues = cards.map(CARD_VALUES_LOW_TO_HIGH::indexOf)

        private val type: HandType by lazy {
            val counts = cards.groupingBy { it }.eachCount()
            val maxCount = counts.values.maxOrNull()!!
            when (maxCount) {
                5 -> HandType.FIVE_OF_A_KIND
                4 -> HandType.FOUR_OF_A_KIND
                3 -> if (counts.size == 2) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
                2 -> if (counts.size == 3) HandType.TWO_PAIR else HandType.PAIR
                else -> HandType.HIGH_CARD
            }
        }

        override fun compareTo(other: Hand): Int {
            var result = type.compareTo(other.type)
            if (result == 0)
                result = cardValues.zip(other.cardValues).map { (a, b) -> a.compareTo(b) }.firstOrNull { it != 0 } ?: 0
            return result
        }

    }

}