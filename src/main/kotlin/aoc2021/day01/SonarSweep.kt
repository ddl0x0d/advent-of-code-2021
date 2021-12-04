package aoc2021.day01

import aoc2021.StreamPuzzle

/**
 * [Day 1: Sonar Sweep](https://adventofcode.com/2021/day/1)
 */
object SonarSweep : StreamPuzzle<Int, Int> {

    override val name = "📡 Sonar Sweep"

    override fun parseInput(input: String): Int = input.toInt()

    /**
     * How many measurements are larger than the previous measurement?
     */
    override fun part1(input: Sequence<Int>): Int = input.windowed(2).count { (a, b) -> a < b }

    /**
     * How many sums are larger than the previous sum?
     */
    override fun part2(input: Sequence<Int>): Int = input.windowed(4).count { (a, _, _, b) -> a < b }
}
