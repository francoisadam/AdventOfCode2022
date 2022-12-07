package day7

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/Day07_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 95437)
    //check(testPart2 == 0)

    val input = readInput("day7/Day07")
    //println("part1 : ${part1(input)}")
    //println("part2 : ${part2(input)}")
}

private data class Dir(
    val name: String,
    var subdirs: MutableList<Dir>,
    var files: MutableList<File>,
) {
    fun getSize(): Int = subdirs.sumOf { it.getSize() } + files.sumOf { it.size }
}

private data class File(
    val name: String,
    val size: Int,
)

private sealed class Command {
    object CdUp: Command()
    data class CdDown(val destinationDir: Dir): Command()
    object CdRoot: Command()
    object Ls: Command()
    object Result: Command()
}

private fun String.detectCommand(): Command {
    if (this.first().toString() == "$") {
        if (this.substring(2,3) == "ls") {
            Command.Ls
        } else {

        }
    } else {
        Command.Result
    }
}