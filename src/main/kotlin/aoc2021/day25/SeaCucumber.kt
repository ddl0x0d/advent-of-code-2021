package aoc2021.day25

import aoc2021.BatchPuzzle

/**
 * [Day 25: Sea Cucumber](https://adventofcode.com/2021/day/25)
 */
object SeaCucumber : BatchPuzzle<Seafloor, Int> {

    override val name = "🥒 Sea Cucumber"

    override fun solve(path: String) {
        println(name)
        println("Part One = ${solve(path, this::part1)}")
        // There is no Part Two for this puzzle
    }

    override fun parseInput(input: List<String>) = Seafloor(input)

    /**
     * What is the first step on which no sea cucumbers move?
     */
    override fun part1(input: Seafloor): Int {
        var moves = 0
        do {
            val moved = input.move()
            moves++
        } while (moved > 0)
        return moves
    }

    override fun part2(input: Seafloor): Int = 0
}

class Seafloor(input: List<String>) {

    private val height = input.size
    private val width = input.firstOrNull()?.length ?: 0
    private val locations: List<Location> = input.flatMapIndexed { y, line ->
        line.toList().mapIndexed { x, char ->
            Location(x, y, type = Location.Type.values().first { it.char == char })
        }
    }

    fun move(): Int {
        val east = move(Location.Type.EAST) { location((it.x + 1) % width, it.y) }
        val south = move(Location.Type.SOUTH) { location(it.x, (it.y + 1) % height) }
        return east + south
    }

    @Suppress("ConvertCallChainIntoSequence")
    private fun move(type: Location.Type, target: (Location) -> Location): Int =
        locations.filter {
            it.type == type
        }.map {
            it to target(it)
        }.filter { (_, target) ->
            target.type == Location.Type.EMPTY
        }.onEach { (current, target) ->
            current.type = Location.Type.EMPTY
            target.type = type
        }.count()

    private fun location(x: Int, y: Int): Location = locations[x + y * width]

    override fun toString() = buildString {
        locations.forEachIndexed { i, location ->
            append(location.type.char)
            if ((i + 1) % width == 0) {
                appendLine()
            }
        }
    }

    data class Location(val x: Int, val y: Int, var type: Type) {
        enum class Type(val char: Char) {
            EAST('>'), SOUTH('v'), EMPTY('.')
        }
    }
}
