fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toRucksack() }.sumOf { it.findDuplicate().priority() }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { it.toElfGroup().findBadge().priority() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 157)
    check(testPart2 == 70)

    val input = readInput("Day03")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
}

private data class Rucksack(
    val firstCompartment: List<Char>,
    val secondCompartment: List<Char>,
) {
    fun findDuplicate(): Char = firstCompartment.first { secondCompartment.contains(it) }
}

private fun String.toRucksack(): Rucksack {
    val allItems = this.toCharArray().toList()
    return Rucksack(
        firstCompartment =  allItems.subList(0, allItems.size / 2),
        secondCompartment =  allItems.subList(allItems.size / 2, allItems.size),
    )
}

private fun Char.priority(): Int = if (this.isLowerCase()) {
    this.code - 96
} else {
    this.code - 38
}

private data class ElfGroup(
    val firstElf: List<Char>,
    val secondElf: List<Char>,
    val thirdElf: List<Char>,
)

private fun List<String>.toElfGroup() = ElfGroup(
    firstElf = this.first().toCharArray().toList(),
    secondElf = this[1].toCharArray().toList(),
    thirdElf = this.last().toCharArray().toList(),
)

private fun ElfGroup.findBadge(): Char {
    val possibleBadges = mutableListOf<Char>()
    possibleBadges.addAll(firstElf)
    possibleBadges.removeIf { !secondElf.contains(it) || !thirdElf.contains(it) }
    return possibleBadges.first()
}