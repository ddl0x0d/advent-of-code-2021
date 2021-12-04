package aoc2021.day11

import aoc2021.BatchPuzzle
import aoc2021.Grid

/**
 * [Day 11: Dumbo Octopus](https://adventofcode.com/2021/day/11)
 */
object DumboOctopus : BatchPuzzle<Cavern, Int> {

    override val name = "🐙 Dumbo Octopus"

    override fun parseInput(input: List<String>) = Cavern(input)

    /**
     * How many total flashes are there after 100 steps?
     */
    override fun part1(input: Cavern): Int {
        repeat(100) { input.gainEnergy() }
        return input.list.sumOf { it.flashed }
    }

    /**
     * What is the first step during which all octopuses flash?
     */
    override fun part2(input: Cavern): Int {
        var step = 0
        while (input.list.any { it.energy > 0 }) {
            input.gainEnergy()
            step++
        }
        return step
    }
}

class Cavern(input: List<String>) : Grid<Cavern.Octopus>(input, Connectivity.OMNIDIRECTIONAL) {

    override fun toCell(x: Int, y: Int, value: Int) = Octopus(x, y, value)

    fun gainEnergy() {
        val flashing = list
            .onEach { it.energy++ }
            .filter { it.energy > 9 }
            .let { ArrayDeque(it) }
        while (flashing.isNotEmpty()) {
            with(flashing.removeFirst()) {
                if (energy > 0) {
                    energy = 0
                    flashed++
                    neighbours(x, y)
                        .filter { it.energy > 0 }
                        .onEach { it.energy++ }
                        .filter { it.energy > 9 }
                        .forEach { flashing.addLast(it) }
                }
            }
        }
    }

    override fun toString() = buildString {
        list.forEach {
            append(if (it.energy > 9) 'X' else it.energy)
            if (it.x + 1 == width) {
                append('\n')
            }
        }
    }

    data class Octopus(val x: Int, val y: Int, var energy: Int) {
        var flashed = 0
    }
}
