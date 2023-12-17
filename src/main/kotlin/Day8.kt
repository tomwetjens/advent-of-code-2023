import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val lines = Path("inputs/day8.txt")
        .readLines()

    val instructions = lines[0].toCharArray()

    val nodes = lines.drop(2)
        .map(Node::parse)
        .associateBy { it.id }

    // part 1
    println(numberOfSteps(nodes, instructions, nodes["AAA"]!!) { it.id == "ZZZ" })

    // part 2
    val startNodes = nodes.filterKeys { it.endsWith('A') }.values
    val distances = startNodes.map { startNode -> numberOfSteps(nodes, instructions, startNode) { it.id.endsWith('Z') } }
    println(distances)
    println(lcm(distances))
}

private fun lcm(list: List<Long>): Long {
    var result = list[0]
    for (i in 1 until list.size) {
        result = lcm(result, list[i])
    }
    return result
}

private fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

private fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

private fun numberOfSteps(nodes: Map<String, Node>, instructions: CharArray, startNode: Node, isEndNode: (Node) -> Boolean): Long {
    var instructionIndex = 0
    var currentNode = startNode
    var steps = 0L
    while (!isEndNode(currentNode)) {
        val instruction = instructions[instructionIndex]
        currentNode = when (instruction) {
            'L' -> nodes[currentNode.left]!!
            'R' -> nodes[currentNode.right]!!
            else -> throw Exception("Unknown instruction $instruction")
        }
        instructionIndex = (instructionIndex + 1) % instructions.size
        steps++
    }
    return steps
}

data class Node(val id: String, val left: String, val right: String) {
    companion object {
        fun parse(line: String): Node {
            val (id, left, right) = line.split(Regex("[ =,()]+"))
            return Node(id, left, right)
        }
    }
}