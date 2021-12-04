package aoc2021.day09

import aoc2021.BatchPuzzle
import aoc2021.Grid

/**
 * [Day 9: Smoke Basin](https://adventofcode.com/2021/day/9)
 */
object SmokeBasin : BatchPuzzle<HeightMap, Int> {

    override val name = "🌋 Smoke Basin"

    override fun parseInput(input: List<String>) = HeightMap(input)

    /**
     * What is the sum of the risk levels of all low points on your heightmap?
     */
    override fun part1(input: HeightMap): Int = input.lowPoints().sumOf { it.riskLevel }

    /**
     * What do you get if you multiply together the sizes of the three largest basins?
     */
    override fun part2(input: HeightMap): Int = input.lowPoints()
        .map { it.basin().size }.sortedDescending().take(3).reduce { a, b -> a * b }
}

class HeightMap(input: List<String>) : Grid<HeightMap.Location>(input, Connectivity.PERPENDICULAR) {

    override fun toCell(x: Int, y: Int, value: Int) = Location(x, y, value)

    fun lowPoints() = list.filter { it.lowPoint }

    inner class Location(
        private val x: Int,
        private val y: Int,
        private val height: Int,
    ) {
        val id = "$x:$y"
        val lowPoint: Boolean by lazy {
            neighbours.all { it.height > height }
        }
        val riskLevel: Int by lazy {
            if (lowPoint) height + 1 else 0
        }

        fun basin(basin: MutableSet<Location> = mutableSetOf()): Set<Location> =
            if (this in basin) {
                basin
            } else {
                basin += this
                neighbours.filter { it.height < 9 }.forEach { it.basin(basin) }
                basin
            }

        private val neighbours: List<Location> by lazy { neighbours(x, y) }

        override fun equals(other: Any?): Boolean = other is Location && this.id == other.id
        override fun hashCode(): Int = id.hashCode()
    }
}
