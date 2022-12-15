package day7

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val dirMap = mutableMapOf<String, MutableList<File>>()
        val rootPath = "/"
        var currentDirPath = rootPath
        var remainingLines = input
        while (remainingLines.isNotEmpty()) {
            when (val command = remainingLines.first().detectCommand()) {
                is Command.CdUp -> if (currentDirPath != rootPath) {
                    currentDirPath = currentDirPath.split("/").dropLast(1).joinToString("/")
                }
                is Command.CdDown -> currentDirPath += if (currentDirPath == rootPath) {
                    command.destinationDirName
                } else {
                    "/" + command.destinationDirName
                }
                is Command.CdRoot -> currentDirPath = rootPath
                is Command.Ls -> {} //go to next command
                is Command.Result -> {
                    if (!command.isDir) {
                        val fileList = dirMap.getOrDefault(currentDirPath, mutableListOf())
                        fileList.add(File(name = command.name, size = command.size))
                        dirMap[currentDirPath] = fileList
                    }
                }
            }
            remainingLines = remainingLines.drop(1)
        }

        val dirSizes = mutableMapOf<String, Long>()
        dirMap.forEach { (path, files) ->
            val filesSize = files.sumOf { it.size }
            dirSizes.forEach { (dirPath, dirSize) ->
                if (path.startsWith(dirPath)) {
                    dirSizes[dirPath] = dirSize + filesSize
                }
            }
            dirSizes[path] = filesSize
        }

        return dirSizes.filter { it.value <= 100000 }.map { it.value }.sum()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/Day07_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 95437L)
    //check(testPart2 == 0)

    val input = readInput("day7/Day07")
    println("part1 : ${part1(input)}") //TODO should get 1243729
    //println("part2 : ${part2(input)}")
}

private data class File(
    val name: String,
    val size: Long,
)

private sealed class Command {
    object CdUp: Command()
    data class CdDown(val destinationDirName: String): Command()
    object CdRoot: Command()
    object Ls: Command()
    data class Result(val isDir: Boolean, val size: Long, val name: String): Command()
}

private fun String.detectCommand(): Command {
    if (this.first().toString() != "$") {
        return Command.Result(
            isDir = this.substring(0, 3) == "dir",
            size = this.split(" ").first().toLongOrNull() ?: 0L,
            name = this.split(" ").last(),
        )
    }
    return when {
        this.substring(2,4) == "ls" -> Command.Ls
        this.substring(5) == "/" -> Command.CdRoot
        this.substring(5) == ".." -> Command.CdUp
        else -> Command.CdDown(this.substring(5))
    }
}