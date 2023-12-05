import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val grid = Path("inputs/day3.txt")
        .readLines()
        .foldIndexed(Grid(), LineParser::parse)

    grid.print(140, 140)

    // part 1
    val partNumbers = grid.numbers
        .filter { (start, value) -> grid.hasAdjacentSymbol(start, value.length) }
    println(partNumbers.values.sumOf { partNumber -> partNumber.toInt() })

    // part 2
    println(grid.symbols.filterValues { it == '*' }
        .mapNotNull { (symbol, _) ->
            val adjacent = grid.numbers
                .filter { (start, value) ->
                    (start.x..<start.x + value.length).any { x -> symbol.isAdjacent(Point(x, start.y)) }
                }
                .values
                .toList()

            if (adjacent.size == 2)
                adjacent[0].toInt() * adjacent[1].toInt()
            else null
        }.sum())
}

data class LineParser(val y: Int, val grid: Grid, val x: Int = 0) {

    companion object {
        val regex = Regex("[0-9]+|\\.+|[^0-9.]+")

        fun parse(y: Int, grid: Grid, line: String): Grid {
            val segments = regex.findAll(line).map { it.value }.toList()
            return segments.fold(LineParser(y, grid), LineParser::nextSegment).grid
        }
    }

    fun nextSegment(segment: String): LineParser {
        val point = Point(x, y)
        return if (segment.startsWith('.')) {
            copy(x = x + segment.length)
        } else if (segment.first().isDigit()) {
            copy(x = x + segment.length, grid = grid.addPartNumber(point, segment))
        } else {
            copy(x = x + segment.length, grid = grid.addSymbol(point, segment.first()))
        }
    }
}

data class Point(val x: Int, val y: Int) {
    fun isAdjacent(other: Point) =
        (x == other.x - 1 && y == other.y)
                || (x == other.x + 1 && y == other.y)
                || (x == other.x && y == other.y + 1)
                || (x == other.x && y == other.y - 1)
                || (x == other.x - 1 && y == other.y - 1)
                || (x == other.x + 1 && y == other.y + 1)
                || (x == other.x - 1 && y == other.y + 1)
                || (x == other.x + 1 && y == other.y - 1)
}

data class Grid(
    val numbers: Map<Point, String> = emptyMap(),
    val symbols: Map<Point, Char> = emptyMap()
) {
    fun addPartNumber(start: Point, value: String) = copy(numbers = numbers + Pair(start, value))
    fun addSymbol(point: Point, value: Char) = copy(symbols = symbols + Pair(point, value))

    fun print(width: Int, height: Int) {
        for (y in 0..height - 1) {
            var x = 0
            while (x <= width - 1) {
                val point = Point(x, y)
                val number = numbers[point]

                if (number != null) {
                    print(if (hasAdjacentSymbol(point, number.length)) "\u001B[32m" else "\u001B[31m")
                    print(number)
                    print("\u001B[0m")
                    x += number.length
                } else {
                    val symbol = symbols[point] ?: '.'
                    print(symbol)
                    x++
                }
            }
            println()
        }
    }

    fun hasAdjacentSymbol(start: Point, length: Int): Boolean =
        (start.x - 1..start.x + length).any { x ->
            symbols.contains(Point(x, start.y - 1))
                    || symbols.contains(Point(x, start.y + 1))
        } || symbols.contains(Point(start.x - 1, start.y))
                || symbols.contains(Point(start.x + length, start.y))
}