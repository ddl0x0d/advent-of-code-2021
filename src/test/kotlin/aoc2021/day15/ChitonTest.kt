package aoc2021.day15

import aoc2021.PuzzleTest
import aoc2021.day15.Chiton.rotate
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

class ChitonTest : PuzzleTest<List<String>, Int>(
    puzzle = Chiton,
    path = "examples/day-15.txt",
    part1 = 40,
    part2 = 315,
) {
    @ParameterizedTest
    @MethodSource("lowestTotalRiskArguments")
    fun `lowest total risk`(input: String) {
        val result = Cavern(input.split("\n")).lowestTotalRisk()

        assertThat(result).isEqualTo(0)
    }

    private fun lowestTotalRiskArguments() = listOf(
        """
        00000
        99990
        00000
        09999
        00000
        """,
        """
        09000
        09090
        09090
        09090
        00090
        """,
        """
        0000000
        9999990
        0000090
        0999090
        0900090
        0909990
        0900000
        0999999
        0000000
        """,
    ).map(String::trimIndent)

    @ParameterizedTest
    @CsvSource(
        "123456789,  0, 123456789",
        "123456789,  1, 234567891",
        "123456789,  5, 678912345",
        "123456789, 10, 123456789",
        "123456789, 11, 234567891",
        "123456789, 15, 678912345",
        "123456789, 20, 123456789",
    )
    fun rotate(input: String, positions: Int, output: String) {
        assertThat(input.rotate(positions)).isEqualTo(output)
    }
}
