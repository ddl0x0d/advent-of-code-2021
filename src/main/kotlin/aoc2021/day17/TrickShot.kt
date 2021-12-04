package aoc2021.day17

import aoc2021.BatchPuzzle
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * [Day 17: Trick Shot](https://adventofcode.com/2021/day/17)
 */
object TrickShot : BatchPuzzle<TargetArea, Int> {

    override val name = "⛳ Trick Shot"

    private val areaRegex = "target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)".toRegex()

    override fun parseInput(input: List<String>): TargetArea {
        val (minX, maxX, minY, maxY) = areaRegex.matchEntire(input.first())?.destructured!!
        return TargetArea(minX.toInt(), maxX.toInt(), minY.toInt(), maxY.toInt())
    }

    /**
     * What is the highest y position it reaches on this trajectory?
     */
    override fun part1(input: TargetArea): Int {
        val startY = input.y1.absoluteValue - 1
        return (1 + startY) * startY / 2
    }

    /**
     * How many distinct initial velocity values cause the
     * probe to be within the target area after any step?
     */
    override fun part2(input: TargetArea): Int {
        // s = sum(1..x) = (1 + x) * x / 2 -> x^2 + x - 2s = 0
        val txMax = calculateIntRange(input.x1, input.x2) { quadraticEquation(1.0, 1.0, -2.0 * it) }
        val tyMax = input.y1.absoluteValue * 2
        return (1..max(txMax.last, tyMax)).map { t ->
            val vxs = calculateIntRange(input.x1, input.x2) { velocity(it, min(t, txMax.last)) }
            val vys = calculateIntRange(input.y1, input.y2) { velocity(it, t) }
            vxs to vys
        }.fold(mutableMapOf<Int, MutableSet<Int>>()) { map, ranges ->
            // deduplicate
            map.apply {
                ranges.first.forEach { vx ->
                    ranges.second.forEach { vy ->
                        computeIfAbsent(vx) { mutableSetOf() } += vy
                    }
                }
            }
        }.values.sumOf { it.size }
    }

    private fun calculateIntRange(first: Int, second: Int, calculation: (Double) -> Double) = IntRange(
        start = ceil(calculation(first.toDouble())).toInt(),
        endInclusive = floor(calculation(second.toDouble())).toInt(),
    )

    private fun quadraticEquation(a: Double, b: Double, c: Double): Double {
        val d = b * b - 4 * a * c
        return (-b + sqrt(d)) / (2 * a)
    }

    /**
     * s = v * t - (t - 1) * t / 2
     */
    private fun velocity(distance: Double, time: Int): Double = distance / time + (time.toDouble() - 1) / 2
}

data class TargetArea(
    val x1: Int, val x2: Int,
    val y1: Int, val y2: Int,
)
