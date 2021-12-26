package aoc2021

import java.io.File

interface Puzzle<INPUT, OUTPUT> {
    val name: String
    fun solve(path: String) {
        println(name)
        println("Part One = ${solve(path, this::part1)}")
        println("Part Two = ${solve(path, this::part2)}")
    }

    fun solve(path: String, part: (INPUT) -> OUTPUT): OUTPUT
    fun part1(input: INPUT): OUTPUT
    fun part2(input: INPUT): OUTPUT
    fun readInput(path: String): INPUT
}

/**
 * Puzzle that reads whole input in a single batch
 */
interface BatchPuzzle<INPUT, OUTPUT> : Puzzle<INPUT, OUTPUT> {
    override fun solve(path: String, part: (INPUT) -> OUTPUT): OUTPUT = part(readInput(path))
    override fun readInput(path: String): INPUT = parseInput(readLines(path))
    fun parseInput(input: List<String>): INPUT
}

/**
 * Puzzle that reads input as stream of lines
 */
interface StreamPuzzle<INPUT, OUTPUT> : Puzzle<Sequence<INPUT>, OUTPUT> {
    override fun solve(path: String, part: (Sequence<INPUT>) -> OUTPUT): OUTPUT =
        File(path).useLines(charset = Charsets.UTF_8) {
            part(it.map(this::parseInput))
        }

    override fun readInput(path: String): Sequence<INPUT> =
        readLines(path).asSequence().map(this::parseInput)

    fun parseInput(input: String): INPUT
}

fun readLines(path: String) = File(path).readLines()
