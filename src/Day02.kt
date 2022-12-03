import Outcome.DRAW
import Outcome.LOOSE
import Outcome.WIN
import Shape.PAPER
import Shape.ROCK
import Shape.SCISSOR

fun main() {
    fun part1(input: List<String>): Int {
        val rounds = input.map {
            val rawRound = it.split(" ")
            Round(
                yourShape = rawRound.last().toShape(),
                opponentShape = rawRound.first().toShape(),
            )
        }

        return rounds.sumOf { it.result() }
    }

    fun part2(input: List<String>): Int {
        val rounds = input.map {
            val rawRound = it.split(" ")
            val opponentShape = rawRound.first().toShape()
            Round(
                yourShape = rawRound.last().toOutcome().neededShapeAgainst(opponentShape),
                opponentShape = opponentShape,
            )
        }

        return rounds.sumOf { it.result() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 15)
    check(testPart2 == 12)

    val input = readInput("Day02")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
}

private data class Round(
    val yourShape: Shape,
    val opponentShape: Shape,
) {

    fun result(): Int = yourShape.outcomeAgainst(opponentShape).value + yourShape.value
}

private enum class Shape(val value: Int) {
    ROCK(1), PAPER(2), SCISSOR(3);

    fun outcomeAgainst(opponentShape: Shape): Outcome = when (this) {
        ROCK -> when (opponentShape) {
            ROCK -> DRAW
            PAPER -> LOOSE
            SCISSOR -> WIN
        }
        PAPER -> when (opponentShape) {
            ROCK -> WIN
            PAPER -> DRAW
            SCISSOR -> LOOSE
        }
        SCISSOR -> when (opponentShape) {
            ROCK -> LOOSE
            PAPER -> WIN
            SCISSOR -> DRAW
        }
    }
}

private enum class Outcome(val value: Int) {
    WIN(6), DRAW(3), LOOSE(0);

    fun neededShapeAgainst(opponentShape: Shape): Shape = Shape.values().first {
        it.outcomeAgainst(opponentShape) == this
    }
}

private fun String.toOutcome(): Outcome = when (this) {
    "X" -> LOOSE
    "Y" -> DRAW
    else -> WIN
}

private fun String.toShape(): Shape = when (this) {
    "A", "X" -> ROCK
    "B", "Y" -> PAPER
    else -> SCISSOR
}