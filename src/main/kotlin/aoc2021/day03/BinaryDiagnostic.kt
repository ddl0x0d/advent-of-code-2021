package aoc2021.day03

import aoc2021.BatchPuzzle
import kotlin.math.pow

/**
 * [Day 3: Binary Diagnostic](https://adventofcode.com/2021/day/3)
 */
object BinaryDiagnostic : BatchPuzzle<List<String>, Int> {

    override val name = "💾 Binary Diagnostic"

    override fun parseInput(input: List<String>) = input

    /**
     * What is the power consumption of the submarine?
     */
    override fun part1(input: List<String>): Int {
        val numDigits = input.firstOrNull()?.length ?: 0
        val ones = Array(numDigits) { index ->
            input.map { it[index] }.count { it == '1' }
        }
        val majority = input.size.toFloat() / 2
        val gamma = ones.joinToString("") { if (it >= majority) "1" else "0" }.toInt(2)
        val epsilon = (2.0.pow(ones.size) - 1).toInt() - gamma
        return gamma * epsilon
    }

    /**
     * What is the life support rating of the submarine?
     */
    override fun part2(input: List<String>): Int {
        val sortedInput = input.sorted()
        val oxygen = findEntry(sortedInput, 0) { zeroes, ones -> zeroes > ones }
        val carbonDioxide = findEntry(sortedInput, 0) { zeroes, ones -> zeroes <= ones }
        return oxygen.toInt(2) * carbonDioxide.toInt(2)
    }

    private fun findEntry(
        input: List<String>,
        index: Int,
        takeZeroes: (Int, Int) -> Boolean
    ): String = if (input.size == 1) {
        input.first()
    } else {
        val zeroes = input.takeWhile { it[index] == '0' }
        val ones = input.subList(zeroes.size, input.size)
        val nextInput = if (takeZeroes(zeroes.size, ones.size)) zeroes else ones
        findEntry(nextInput, index + 1, takeZeroes)
    }
}
