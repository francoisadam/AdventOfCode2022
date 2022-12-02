fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 24000)
    check(testPart2 == 45000)

    val input = readInput("Day01")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
}

fun part1(input: List<String>): Int {
    val elvesSupplies = input.joinToString(",")
        .split(",,")
        .map { elfSupplies ->
            elfSupplies.split(",").sumOf { it.toInt() }
        }
    return elvesSupplies.max()
}

fun part2(input: List<String>): Int {
    val elvesSupplies = input.joinToString(",")
        .split(",,")
        .map { elfSupplies ->
            elfSupplies.split(",").sumOf { it.toInt() }
        }
        .sortedDescending()
    return elvesSupplies[0] + elvesSupplies[1] + elvesSupplies[2]
}