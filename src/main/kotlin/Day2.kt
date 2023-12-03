import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.max

val games = Path("inputs/day2.txt")
    .readLines()
    .map(Game::parse)

val total = mapOf("red" to 12, "green" to 13, "blue" to 14)

fun main() {
    // part 1
    println(games
        .filter { game -> total.containsAmounts(game.grabs.reduce(Grab::maxAmounts).cubes) }
        .sumOf { game -> game.number }
    )

    // part 2
    println(games
        .map { game -> game.grabs.reduce(Grab::maxAmounts).cubes }
        .sumOf { cubes -> cubes.power() })
}

typealias Color = String

typealias Cubes = Map<Color, Int>

fun Cubes.maxAmounts(other: Cubes): Cubes {
    return (this.keys + other.keys)
        .associateWith { color -> max(this[color]?:0, other[color]?: 0) }
}

fun Cubes.containsAmounts(other: Cubes): Boolean {
    return other.all { (color, amount) -> (this[color]?: 0) >= amount }
}

fun Cubes.power(): Int {
    return this.values.reduce(Int::times)
}

data class Grab(val cubes: Cubes) {
    companion object {
        fun parse(s: String): Grab {
            val split = s.split(Regex("[, ]+"))
            return Grab(split
                .windowed(2,2)
                .associate { (amount, color) -> color to amount.toInt() })
        }
    }

    fun maxAmounts(other: Grab): Grab {
        return Grab(this.cubes.maxAmounts(other.cubes))
    }
}

data class Game(val number: Int, val grabs: Set<Grab>) {
    companion object {
        fun parse(line: String): Game {
            val split = line.split(Regex("(Game|[:;]) "))
            return Game(split[1].toInt(), split.drop(2).map(Grab::parse).toSet())
        }
    }
}