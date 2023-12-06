import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow

fun main() {
    val scratchcards = Path("inputs/day4.txt")
        .readLines()
        .map(Scratchcard::parse)
        .associateBy(Scratchcard::cardNumber)

    // part 1
    println(scratchcards.values.sumOf(Scratchcard::points))

    // part 2
    var cache = mutableMapOf<Int, Int>()
    println(scratchcards
        .values
        .sumOf { scratchcard -> scratchcard.numberOfCopies(scratchcards, cache) })
}

data class Scratchcard(val cardNumber: Int, val winningNumbers: Set<Int>, val myNumbers: Set<Int>) {
    companion object {
        fun parse(s: String): Scratchcard {
            val (first, second) = s.split(" | ")
            val split = first.trim().split(Regex("[: ]+"))
            val cardNumber = split[1].toInt()
            val winningNumbers = split.drop(2).map(String::toInt).toSet()
            val myNumbers = second.trim().split(Regex("[ ]+")).map(String::toInt).toSet()
            return Scratchcard(cardNumber, winningNumbers, myNumbers)
        }
    }

    fun points(): Int {
        val matches = matches()
        return 2.0.pow((matches.size - 1).toDouble()).toInt()
    }

    private fun matches() = myNumbers.intersect(winningNumbers)

    fun numberOfCopies(scratchcards: Map<Int, Scratchcard>, cache: MutableMap<Int, Int>): Int {
        return cache.getOrPut(cardNumber) {
            return 1 + (cardNumber + 1..cardNumber + matches().size)
                .mapNotNull(scratchcards::get)
                .sumOf { card -> card.numberOfCopies(scratchcards, cache) }
        }
    }
}