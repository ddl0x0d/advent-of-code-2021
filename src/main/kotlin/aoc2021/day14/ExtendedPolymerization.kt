package aoc2021.day14

import aoc2021.BatchPuzzle

/**
 * [Day 14: Extended Polymerization](https://adventofcode.com/2021/day/14)
 */
object ExtendedPolymerization : BatchPuzzle<Instruction, Long> {

    override val name = "🧪 Extended Polymerization"

    private val regex = "(\\w{2}) -> (\\w)".toRegex()

    override fun parseInput(input: List<String>) = Instruction(
        template = input.first(),
        replace = input.drop(2).mapNotNull {
            regex.matchEntire(it)?.destructured
        }.associate { (pair, insert) ->
            pair to listOf(pair[0] + insert, insert + pair[1])
        }
    )

    /**
     * What do you get if you take the quantity of the most common
     * element and subtract the quantity of the least common element?
     */
    override fun part1(input: Instruction): Long = solve(input, 10)

    /**
     * What do you get if you take the quantity of the most common
     * element and subtract the quantity of the least common element?
     */
    override fun part2(input: Instruction): Long = solve(input, 40)

    private fun solve(input: Instruction, steps: Int): Long {
        var pairCount = input.template.windowed(2)
            .groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() }

        pairCount = generateSequence(pairCount) {
            it.entries.fold(mutableMapOf()) { map, (oldPair, count) ->
                map.apply {
                    input.replace[oldPair]?.forEach { newPair ->
                        increment(newPair, count)
                    }
                }
            }
        }.drop(steps).first()

        val elementCount = pairCount.entries.fold(mutableMapOf<Char, Long>()) { map, (pair, count) ->
            map.apply { increment(pair.first(), count) }
        }.also {
            it.increment(input.template.last())
        }

        val max = elementCount.maxOf { it.value }
        val min = elementCount.minOf { it.value }
        return max - min
    }

    private fun <T> MutableMap<T, Long>.increment(key: T, inc: Long = 1) {
        compute(key) { _, amount ->
            (amount ?: 0) + inc
        }
    }
}

data class Instruction(
    val template: String,
    val replace: Map<String, List<String>>,
)
