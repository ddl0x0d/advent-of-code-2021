package aoc2021

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(PER_CLASS)
abstract class PuzzleTest<INPUT, OUTPUT>(
    val puzzle: Puzzle<INPUT, OUTPUT>,
    private val path: String,
    private val part1: OUTPUT,
    private val part2: OUTPUT,
) {

    @ParameterizedTest
    @MethodSource("arguments")
    fun `example solutions`(part: (INPUT) -> OUTPUT, expected: OUTPUT) {
        val input = puzzle.readInput(path)

        val actual = part(input)

        assertThat(actual).isEqualTo(expected)
    }

    private fun arguments() = listOf(
        puzzle::part1 to part1,
        puzzle::part2 to part2,
    ).map { Arguments.of(it.first, it.second) }
}
