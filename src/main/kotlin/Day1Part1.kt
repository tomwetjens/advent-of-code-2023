import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    println(
        Path("inputs/day1.txt")
            .readLines()
            .map { line -> line.replace(Regex("[^0-9]"), "") }
            .sumOf { digits -> (digits[0] + "" + digits.last()).toInt() }
    )
}
