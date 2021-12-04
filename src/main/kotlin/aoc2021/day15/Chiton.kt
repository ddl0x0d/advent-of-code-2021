package aoc2021.day15

import aoc2021.BatchPuzzle
import aoc2021.Grid
import java.util.PriorityQueue
import kotlin.math.min

/**
 * [Day 15: Chiton](https://adventofcode.com/2021/day/15)
 */
object Chiton : BatchPuzzle<List<String>, Int> {

    override val name = "🐚 Chiton"

    override fun parseInput(input: List<String>) = input

    /**
     * What is the lowest total risk of any path from the top left to the bottom right?
     */
    override fun part1(input: List<String>): Int = Cavern(input).lowestTotalRisk()

    /**
     * What is the lowest total risk of any path from the top left to the bottom right?
     */
    override fun part2(input: List<String>): Int = Cavern(
        input = (0 until 5).flatMap { y ->
            input.map {
                (0 until 5).joinToString("") { x ->
                    it.rotate(x)
                }
            }.map {
                it.rotate(y)
            }
        }
    ).lowestTotalRisk()

    fun String.rotate(positions: Int): String = toList()
        .map { it.digitToInt() + positions % 10 }
        .map { if (it > 9) it - 9 else it }
        .joinToString("")
}

class Cavern(input: List<String>) : Grid<Cavern.Position>(input, Connectivity.PERPENDICULAR) {

    override fun toCell(x: Int, y: Int, value: Int) = Position(x, y, value)

    fun lowestTotalRisk(): Int {
        val start = list.first().apply { minTotalRisk = 0 }
        val visited = mutableSetOf<Position>()
        val queue = PriorityQueue<Position>().apply { offer(start) }
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            if (next !in visited) {
                neighbours(next.x, next.y).onEach {
                    it.minTotalRisk = min(it.minTotalRisk, it.riskLevel + next.minTotalRisk)
                }.filterNot {
                    it in visited
                }.forEach(queue::offer)
                visited += next
            }
        }
        return list.last().minTotalRisk
    }

    class Position(val x: Int, val y: Int, val riskLevel: Int) : Comparable<Position> {
        val id = "$x:$y"
        var minTotalRisk = Int.MAX_VALUE

        override fun equals(other: Any?): Boolean = other is Position && id == other.id
        override fun hashCode(): Int = id.hashCode()
        override fun compareTo(other: Position): Int = compareBy<Position> { it.minTotalRisk }.compare(this, other)
    }
}
