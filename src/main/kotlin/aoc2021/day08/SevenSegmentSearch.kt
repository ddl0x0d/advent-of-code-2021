package aoc2021.day08

import aoc2021.StreamPuzzle

/**
 * [Day 8: Seven Segment Search](https://adventofcode.com/2021/day/8)
 */
object SevenSegmentSearch : StreamPuzzle<Entry, Int> {

    override val name = "🎰 Seven Segment Search"

    override fun parseInput(input: String): Entry {
        val (signals, digits) = input.split(" | ", limit = 2)
        return Entry(
            signals = signals.split(" ").map { Digit(it) },
            digits = digits.split(" ").map { Digit(it) }
        )
    }

    private val distinguishedDigits = setOf(2, 3, 4, 7)

    /**
     * In the output values, how many times do digits 1, 4, 7, or 8 appear?
     */
    override fun part1(input: Sequence<Entry>): Int =
        input.flatMap { it.digits }.map { it.size }.count { it in distinguishedDigits }

    /**
     * What do you get if you add up all of the output values?
     */
    override fun part2(input: Sequence<Entry>): Int =
        input.onEach(this::deduce).sumOf { it.output() }

    private fun deduce(input: Entry) {
        val (signals, digits) = input
        signals.apply {
            first { it.size == 2 }.value = 1
            val seven = first { it.size == 3 }.apply { value = 7 }
            val four = first { it.size == 4 }.apply { value = 4 }
            first { it.size == 7 }.value = 8
            first { it.size == 5 && seven in it }.value = 3
            val six = first { it.size == 6 && seven !in it }.apply { value = 6 }
            first { it.size == 6 && four in it }.value = 9
            first { it.size == 6 && it.value == null }.apply { value = 0 }
            first { it.size == 5 && it in six }.value = 5
            first { it.size == 5 && it.value == null }.value = 2
        }
        digits.forEach { digit ->
            digit.value = signals.first { it == digit }.value
        }
    }
}

data class Entry(
    val signals: List<Digit>,
    val digits: List<Digit>,
) {
    fun output(): Int = digits.joinToString("") { it.value!!.toString() }.toInt()
}

class Digit(digits: String) {
    private val signals = digits.toSet()
    val size = signals.size
    var value: Int? = null

    operator fun contains(other: Digit): Boolean = signals.containsAll(other.signals)

    override operator fun equals(other: Any?): Boolean = other is Digit && signals == other.signals

    override fun toString(): String = "$signals = $value"
}
