package day8

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.toMatrix()
        var visibleTreesCount = 0
        val visibleTrees = mutableListOf<String>()
        matrix.forEachIndexed { i, line ->
            line.forEachIndexed { j, tree ->
                val column = matrix.getColumnAtIndex(j)
                val visible = line.subList(0, j).all { it < tree }
                        || line.subList(j + 1, line.size).all { it < tree }
                        || column.subList(0, i).all { it < tree }
                        || column.subList(i + 1, column.size).all { it < tree }
                if (visible) {
                    visibleTreesCount++
                    visibleTrees.add("tree ligne $i & colonne $j")
                }
            }
        }
        return visibleTreesCount
    }

    fun part2(input: List<String>): Int {
        val matrix = input.toMatrix()
        val treesMap = mutableListOf<TreeView>()
        matrix.forEachIndexed { i, line ->
            line.forEachIndexed { j, tree ->
                val column = matrix.getColumnAtIndex(j)
                treesMap.add(
                    TreeView(
                        upTrees = column.subList(0, i).reversed().countVisibleTrees(tree),
                        downTrees = column.subList(i + 1, column.size).countVisibleTrees(tree),
                        leftTrees = line.subList(0, j).reversed().countVisibleTrees(tree),
                        rightTrees = line.subList(j + 1, line.size).countVisibleTrees(tree),
                    )
                )
            }
        }
        return treesMap.maxOfOrNull { it.scenicScore() } ?: 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day8/Day08_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 21)
    check(testPart2 == 8)

    val input = readInput("day8/Day08")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
}

private fun List<String>.toMatrix(): List<List<Int>> = this.map { line ->
    line.toCharArray().map { it.digitToInt() }
}

private fun List<List<Int>>.getColumnAtIndex(index: Int): List<Int> = this.map { line ->
    line[index]
}

private data class TreeView(
    val upTrees: Int,
    val downTrees: Int,
    val leftTrees: Int,
    val rightTrees: Int,
) {
    fun scenicScore(): Int = upTrees * downTrees * leftTrees * rightTrees
}

private fun Iterable<Int>.countVisibleTrees(maxHeight: Int): Int = this.withIndex().takeWhile {
    it.index == 0 || this.toList()[it.index - 1] < maxHeight
}.count()