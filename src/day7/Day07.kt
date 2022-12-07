package day7

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        //TODO utiliser une map avec des clés en /a/b/c à la place
        val dirMap = mutableMapOf<String, List<File>>()
        val currentDirPath = "/"
        val fileList = mutableListOf<File>()
        val remainingLines = input.toMutableList()
        while (remainingLines.isNotEmpty()) {
            val command = remainingLines.first().detectCommand()
            when (command) {
                is Command.CdUp -> {} //TODO change currentDirPath
                is Command.CdDown -> {} //TODO change
                is Command.CdRoot -> {} //TODO make currentDirPath root
                is Command.Ls -> {} //TODO Reset fileList and go next
                is Command.Result -> {} //TODO if dir skip else add to fileList
            }
            remainingLines.drop(1)
        }
        //TODO crawl through dirMap to create map<dir,size> and return
        return 0
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

private data class File(
    val name: String,
    val size: Int,
)

private sealed class Command {
    object CdUp: Command()
    data class CdDown(val destinationDirName: String): Command()
    object CdRoot: Command()
    object Ls: Command()
    object Result: Command()
}

private fun String.detectCommand(): Command {
    if (this.first().toString() != "$") {
        return Command.Result
    }
    return when {
        this.substring(2,3) == "ls" -> Command.Ls
        this.substring(5) == "/" -> Command.CdRoot
        this.substring(5) == ".." -> Command.CdUp
        else -> Command.CdDown(this.substring(5))
    }
}

private fun String.isDir(): Boolean = this.substring(0, 2) == "dir"

private fun String.detectDir(currentPath: String): String =
    currentPath + "/" + this.substring(3)

private fun String.detectFile(): File = this.split(" ").let {
    File(
        name = it.last(),
        size = it.first().toInt(),
    )
}