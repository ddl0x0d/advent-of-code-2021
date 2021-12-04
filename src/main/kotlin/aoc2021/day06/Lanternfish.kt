package aoc2021.day06

import aoc2021.BatchPuzzle

/**
 * [Day 6: Lanternfish](https://adventofcode.com/2021/day/6)
 */
object Lanternfish : BatchPuzzle<List<Int>, Long> {

    override val name = "🐠 Lanternfish"

    override fun parseInput(input: List<String>): List<Int> =
        input.first().split(",").map { it.toInt() }

    /**
     * How many lanternfish would there be after 80 days?
     */
    override fun part1(input: List<Int>): Long = input.sumOf { spawns(it, 80) }

    /**
     * How many lanternfish would there be after 256 days?
     */
    override fun part2(input: List<Int>): Long = input.sumOf { spawns(it, 256) }

    fun spawns(
        age: Int, days: Int,
        memo: MutableMap<String, Long> = mutableMapOf()
    ): Long = memo.getOrPut("$age-$days") {
        if (age < days) {
            val daysLeft = days - age - 1
            spawns(6, daysLeft, memo) + spawns(8, daysLeft, memo)
        } else {
            1
        }
    }
}
