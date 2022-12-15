package day7

import readInput
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.div

fun main() {
    fun part1(input: List<String>): Long {
        val dirMap = getDirAndFiles(input)
        val dirSizes = getDirSizes(dirMap)
        return dirSizes.filter { it.value <= 100000 }.map { it.value }.sum()
    }

    fun part2(input: List<String>): Long {
        val dirMap = getDirAndFiles(input)
        val dirSizes = getDirSizes(dirMap)
        val unusedSpace = 70000000 - (dirSizes[Paths.get("/")] ?: 0L)
        val neededSpace = 30000000 - unusedSpace
        return dirSizes.values.filter { it > neededSpace }.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/Day07_test")
    val testPart1 = part1(testInput)
    println("testPart1: $testPart1")
    val testPart2 = part2(testInput)
    println("testPart2: $testPart2")
    check(testPart1 == 95437L)
    check(testPart2 == 24933642L)

    val input = readInput("day7/Day07")
    println("part1 : ${part1(input)}")
    println("part2 : ${part2(input)}")
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

private fun MutableList<File>.totalSize(): Long = this.sumOf { it.size }

private fun Path.isParent(potentialChild: Path): Boolean = potentialChild.startsWith(this)

private fun getDirAndFiles(input: List<String>): MutableMap<Path, MutableList<File>> {
    val dirMap = mutableMapOf<Path, MutableList<File>>()
    val rootPath = Paths.get("/")
    var currentDirPath = rootPath
    var remainingLines = input
    while (remainingLines.isNotEmpty()) {
        when (val command = remainingLines.first().detectCommand()) {
            is Command.CdUp -> currentDirPath = currentDirPath.parent
            is Command.CdRoot -> currentDirPath = rootPath
            is Command.CdDown -> currentDirPath /= command.destinationDirName
            is Command.Ls -> dirMap[currentDirPath] = dirMap.getOrDefault(currentDirPath, mutableListOf())
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
    return dirMap
}

private fun getDirSizes(dirMap: MutableMap<Path, MutableList<File>>): MutableMap<Path, Long> {
    val dirSizes = mutableMapOf<Path, Long>()
    dirMap.forEach { (currentPath, currentFiles) ->
        val childrenSize = dirMap.filter { (path, _) ->
            currentPath.isParent(path) && path != currentPath
        }.map {
            it.value.totalSize()
        }.sum()
        val size = currentFiles.totalSize() + childrenSize
        dirSizes[currentPath] = size
    }
    return dirSizes
}
