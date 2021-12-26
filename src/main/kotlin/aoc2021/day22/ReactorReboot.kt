package aoc2021.day22

import aoc2021.StreamPuzzle

/**
 * [Day 22: Reactor Reboot](https://adventofcode.com/2021/day/22)
 */
object ReactorReboot : StreamPuzzle<RebootStep, Long> {

    override val name = "⚛ Reactor Reboot"

    private val regex = "(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)".toRegex()

    override fun parseInput(input: String): RebootStep {
        val (turnOn, x1, x2, y1, y2, z1, z2) = regex.matchEntire(input)!!.destructured
        return RebootStep(
            turnOn = "on" == turnOn,
            cuboid = Cuboid(
                x = x1.toInt()..x2.toInt(),
                y = y1.toInt()..y2.toInt(),
                z = z1.toInt()..z2.toInt(),
            )
        )
    }

    private val LIMIT_RANGE: IntRange = -50..50
    private val LIMIT_CUBOID = Cuboid(LIMIT_RANGE, LIMIT_RANGE, LIMIT_RANGE)

    /**
     * How many cubes are on?
     */
    override fun part1(input: Sequence<RebootStep>): Long = calculateCubesOn(
        input.map { it.copy(cuboid = it.cuboid intersection LIMIT_CUBOID) }
    )

    /**
     * How many cubes are on?
     */
    override fun part2(input: Sequence<RebootStep>): Long = calculateCubesOn(input)

    private fun calculateCubesOn(input: Sequence<RebootStep>): Long {
        val on = mutableListOf<Cuboid>()
        val off = mutableListOf<Cuboid>()
        input.forEach { (turnOn, cuboid) ->
            val newOn = on.map { it intersection cuboid }.filterNot { it.isEmpty }
            val newOff = off.map { it intersection cuboid }.filterNot { it.isEmpty }
            on += newOff
            off += newOn
            if (turnOn) {
                on += cuboid
            }
        }
        return on.sumOf { it.volume } - off.sumOf { it.volume }
    }
}

infix fun IntRange.intersection(other: IntRange): IntRange =
    if (last >= other.first && first <= other.last) {
        maxOf(first, other.first)..minOf(last, other.last)
    } else {
        IntRange.EMPTY
    }

data class RebootStep(
    val turnOn: Boolean,
    val cuboid: Cuboid,
)

data class Cuboid(
    val x: IntRange,
    val y: IntRange,
    val z: IntRange,
) {
    val isEmpty: Boolean
        get() = ranges.any { it.isEmpty() }

    val volume: Long
        get() = if (isEmpty) 0 else ranges.fold(1L) { acc, range ->
            acc * (range.last - range.first + 1)
        }

    infix fun intersection(other: Cuboid): Cuboid {
        val intersection = ranges.zip(other.ranges) { a, b -> a intersection b }
        return if (intersection.any { it.isEmpty() }) {
            EMPTY
        } else {
            val (x, y, z) = intersection
            Cuboid(x, y, z)
        }
    }

    private val ranges = listOf(x, y, z)

    companion object {
        val EMPTY = Cuboid(IntRange.EMPTY, IntRange.EMPTY, IntRange.EMPTY)
    }
}
