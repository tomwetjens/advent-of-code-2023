import Day7Part2.Hand
import Day7Part2.HandType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Part2Test {

    @Test
    fun types() {
        assertEquals(HandType.FOUR_OF_A_KIND, Hand("QJJQ2").type)
        assertEquals(HandType.PAIR, Hand("32T3K").type)
        assertEquals(HandType.TWO_PAIR, Hand("KK677").type)
        assertEquals(HandType.FOUR_OF_A_KIND, Hand("T55J5").type)
        assertEquals(HandType.FOUR_OF_A_KIND, Hand("KTJJT").type)
        assertEquals(HandType.FOUR_OF_A_KIND, Hand("QQQJA").type)
        assertEquals(HandType.FIVE_OF_A_KIND, Hand("QQQJJ").type)
        assertEquals(HandType.FULL_HOUSE, Hand("QQJAA").type)
        assertEquals(HandType.FULL_HOUSE, Hand("QQAAA").type)
    }

    @Test
    fun breakingTies() {
        assertEquals(-1, Hand("JKKK2").compareTo(Hand("QQQQ2")))
        assertEquals(-1, Hand("T55J5").compareTo(Hand("KTJJT")))
        assertEquals(1, Hand("KTJJT").compareTo(Hand("QQQJA")))
    }
}