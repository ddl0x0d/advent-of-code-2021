package aoc2021.day06

import aoc2021.PuzzleTest
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LanternfishKtTest : PuzzleTest<List<Int>, Long>(
    puzzle = Lanternfish,
    path = "examples/day-06.txt",
    part1 = 5_934,
    part2 = 26_984_457_539,
) {

    @ParameterizedTest
    @CsvSource(
        "1,  0, 1",
        "1,  1, 1",
        "1,  2, 2",
        "0,  0, 1",
        "0,  1, 2",
        "0,  7, 2",
        "0,  8, 3",
        "0, 10, 4",
        "0, 14, 4",
        "0, 15, 5",
        "0, 16, 5",
        "0, 17, 7",
    )
    fun `spawns tests`(age: Int, days: Int, expected: Long) {
        assertThat(Lanternfish.spawns(age, days)).isEqualTo(expected)
    }
}
