import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val regex = Regex("([0-9]|one|two|three|four|five|six|seven|eight|nine)")

    println(
        Path("inputs/day1.txt")
            .readLines()
            .sumOf { line ->
                val first = regex.find(line)!!.value
                val last = (line.length-1 downTo 0).firstNotNullOf { regex.find(line, it) }.value
                (first.toDigit() + "" + last.toDigit()).toInt()
            }
    )
}

fun String.toDigit(): Char =
    when (this) {
        "one" -> '1'
        "two" -> '2'
        "three" -> '3'
        "four" -> '4'
        "five" -> '5'
        "six" -> '6'
        "seven" -> '7'
        "eight" -> '8'
        "nine" -> '9'
        else -> this[0]
    }
