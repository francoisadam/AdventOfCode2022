package day6

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val signal = input.first().toCharArray()
        return List(signal.size) { index ->
            if (index in (3 until signal.size)) {
                if (signal.checkDistinct(index, 4)) {
                    return@List index + 1
                }
            }
            return@List -1
        }.first { it >= 0 }
    }

    fun part2(input: List<String>): Int {
        val signal = input.first().toCharArray()
        return List(signal.size) { index ->
            if (index in (13 until signal.size)) {
                if (signal.checkDistinct(index, 14)) {
                    return@List index + 1
                }
            }
            return@List -1
        }.first { it >= 0 }
    }

    // test if implementation meets criteria from the description, like:
    val test1Input = readInput("day6/Day06_test1")
    val test1Part1 = part1(test1Input)
    val test1Part2 = part2(test1Input)
    val test2Input = readInput("day6/Day06_test2")
    val test2Part1 = part1(test2Input)
    val test2Part2 = part2(test2Input)
    val test3Input = readInput("day6/Day06_test3")
    val test3Part1 = part1(test3Input)
    val test3Part2 = part2(test3Input)
    val test4Input = readInput("day6/Day06_test4")
    val test4Part1 = part1(test4Input)
    val test4Part2 = part2(test4Input)
    val test5Input = readInput("day6/Day06_test5")
    val test5Part1 = part1(test5Input)
    val test5Part2 = part2(test5Input)

    println("test1Part1: $test1Part1")
    check(test1Part1 == 7)
    println("test2Part1: $test2Part1")
    check(test2Part1 == 5)
    println("test3Part1: $test3Part1")
    check(test3Part1 == 6)
    println("test4Part1: $test4Part1")
    check(test4Part1 == 10)
    println("test5Part1: $test5Part1")
    check(test5Part1 == 11)
    println("test1Part2: $test1Part2")
    check(test1Part2 == 19)
    println("test2Part2: $test2Part2")
    check(test2Part2 == 23)
    println("test3Part2: $test3Part2")
    check(test3Part2 == 23)
    println("test4Part2: $test4Part2")
    check(test4Part2 == 29)
    println("test5Part2: $test5Part2")
    check(test5Part2 == 26)

    val input = readInput("day6/Day06")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
}

fun CharArray.checkDistinct(checkIndex: Int, numberOfChar: Int): Boolean = this.toList()
    .subList(checkIndex - numberOfChar + 1, checkIndex + 1)
    .distinct().size == numberOfChar