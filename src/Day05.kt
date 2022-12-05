fun main() {
    fun part1(input: List<String>): String {
        val stacks = input.takeWhile { it.isNotBlank() }
        val crates = stacks.toCrateStacks()
        val instructions = input.drop(stacks.size + 1).map { it.toInstruction() }
        instructions.forEach { instruction -> crates.applyInstruction(instruction) }
        return crates.topValues()
    }

    fun part2(input: List<String>): String {
        val stacks = input.takeWhile { it.isNotBlank() }
        val crates = stacks.toCrateStacks()
        val instructions = input.drop(stacks.size + 1).map { it.toInstruction() }
        instructions.forEach { instruction -> crates.applyNewInstruction(instruction) }
        return crates.topValues()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == "CMZ")
    check(testPart2 == "MCD")

    val input = readInput("Day05")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
}

private fun List<String>.toCrateStacks(): List<MutableList<String>> {
    val indexRow = this.last()
    val numberOfStacks = indexRow.dropLast(1).last().digitToInt()
    val stacks = List<MutableList<String>>(numberOfStacks) { mutableListOf() }

    this.dropLast(1).forEach { row ->
        row.toCrateLine(indexRow).forEachIndexed { index, crate ->
            if (crate != " ") stacks[index].add(crate)
        }
    }
    return stacks
}

private fun String.toCrateLine(indexRow: String): List<String> =
    indexRow.mapIndexed { index, char ->
        if (char != " ".single()) {
            this[index].toString()
        } else {
            "-"
        }
    }.filter { it != "-" }

private fun List<MutableList<String>>.moveCrate(fromIndex: Int, toIndex: Int) = this.apply {
    val crate = get(fromIndex).firstOrNull() ?: " "
    if (crate.isNotBlank()) {
        get(fromIndex).removeFirst()
        get(toIndex).add(0, crate)
    }
}

private fun List<MutableList<String>>.moveMultipleCrates(numberOfCrates:Int, fromIndex: Int, toIndex: Int) = this.apply {
    val crates = get(fromIndex).subList(0, numberOfCrates).toList()
    repeat(numberOfCrates) { get(fromIndex).removeFirst() }
    get(toIndex).addAll(0, crates)
}

private fun List<MutableList<String>>.topValues(): String = this.joinToString("") { it.firstOrNull() ?: "" }

private data class Instruction(
    val repeat: Int,
    val fromIndex: Int,
    val toIndex: Int,
)

private fun String.toInstruction() = this.drop(5)
    .replace(" from ", ",")
    .replace(" to ", ",")
    .split(",")
    .let { row ->
        Instruction(
            repeat = row.first().toInt(),
            fromIndex = row[1].toInt() - 1,
            toIndex = row.last().toInt() - 1,
        )
    }

private fun List<MutableList<String>>.applyInstruction(instruction: Instruction): List<MutableList<String>> =
    this.apply {
        repeat(instruction.repeat) { moveCrate(instruction.fromIndex, instruction.toIndex) }
    }

private fun List<MutableList<String>>.applyNewInstruction(instruction: Instruction): List<MutableList<String>> =
    this.apply {
        moveMultipleCrates(instruction.repeat, instruction.fromIndex, instruction.toIndex)
    }