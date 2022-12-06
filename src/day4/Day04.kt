package day4

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { row ->
            row.split(",").map { it.toAssignment() }.isDuplicate()
        }.map { overlapping -> if (overlapping) 1 else 0 }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { row ->
            row.split(",").map { it.toAssignment() }.isOverlapping()
        }.map { overlapping -> if (overlapping) 1 else 0 }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day4/Day04_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 2)
    check(testPart2 == 4)

    val input = readInput("day4/Day04")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
}

private data class Assignment(
    val fromID: Int,
    val toID: Int,
)

private fun String.toAssignment(): Assignment = this.split("-").let {
    Assignment(
        fromID = it.first().toInt(),
        toID = it.last().toInt(),
    )
}

private fun List<Assignment>.isDuplicate(): Boolean =
    (first().fromID >= last().fromID && first().toID <= last().toID) ||
            (last().fromID >= first().fromID && last().toID <= first().toID)

private fun List<Assignment>.isOverlapping(): Boolean =
    (first().fromID in (last().fromID..last().toID)) ||
            (first().toID in (last().fromID..last().toID)) ||
            (last().fromID in (first().fromID..first().toID)) ||
            (last().toID in (first().fromID..first().toID))