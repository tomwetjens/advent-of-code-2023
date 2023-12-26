import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("inputs/day9.txt")
        .readLines()
        .map { line -> line.split(' ').map(String::toInt) }

    // part 1
    println(input.sumOf { history -> extrapolateRight(history) })

    // part 2
    println(input.sumOf { history -> extrapolateLeft(history) })
}

fun extrapolateRight(sequence: List<Int>): Int {
    val differences = sequence.zipWithNext { a, b -> b - a }
    return if (differences.all { it == 0 }) {
        sequence.last()
    } else {
        sequence.last() + extrapolateRight(differences)
    }
}

fun extrapolateLeft(sequence: List<Int>): Int {
    val differences = sequence.zipWithNext { a, b -> b - a }

    return if (differences.all { it == 0 }) {
        sequence.first()
    } else {
        sequence.first() - extrapolateLeft(differences)
    }
}
