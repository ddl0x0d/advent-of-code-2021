package aoc2021.day07

import aoc2021.BatchPuzzle
import kotlin.math.abs

/**
 * [Day 7: The Treachery of Whales](https://adventofcode.com/2021/day/7)
 */
object TheTreacheryOfWhales : BatchPuzzle<List<Int>, Int> {

    override val name = "🦀 The Treachery of Whales"

    override fun parseInput(input: List<String>): List<Int> =
        input.first().split(",").map { it.toInt() }

    /**
     * How much fuel must they spend to align to that position?
     */
    override fun part1(input: List<Int>): Int {
        val positions = input.groupingBy { it }.eachCount()
            .toMap().map { (position, crabs) -> Position(position, crabs) }
        positions.forEach { start ->
            positions.forEach { end ->
                if (start.position != end.position) {
                    val distance = abs(start.position - end.position)
                    end.fuel += start.crabs * distance
                }
            }
        }
        return positions.minOf { it.fuel }
    }

    /**
     * How much fuel must they spend to align to that position?
     */
    override fun part2(input: List<Int>): Int {
        val positions = input.groupingBy { it }.eachCount()
            .toMap().map { (position, crabs) -> Position(position, crabs) }
            .sortedBy { it.position }
        val min = positions.first().position
        val max = positions.last().position
        return (min..max).minOf { end ->
            positions.sumOf { start ->
                if (start.position != end) {
                    val distance = abs(start.position - end)
                    val fuel = (distance + 1) * (distance.toFloat() / 2)
                    start.crabs * fuel.toInt()
                } else {
                    0
                }
            }
        }
    }
}

data class Position(
    val position: Int,
    val crabs: Int,
    var fuel: Int = 0
)
