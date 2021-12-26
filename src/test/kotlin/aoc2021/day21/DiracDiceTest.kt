package aoc2021.day21

import aoc2021.PuzzleTest
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@Disabled("Runs for too long")
class DiracDiceTest : PuzzleTest<List<Player>, Long>(
    puzzle = DiracDice,
    path = "examples/day-21.txt",
    part1 = 739_785,
    part2 = 444_356_092_776_315,
) {
    @ParameterizedTest
    @CsvSource(
        "1, 0, 1, 2, 2",
        "1, 0, 9, 10, 10",
        "1, 0, 10, 1, 1",
        "1, 0, 19, 10, 10",
        "1, 10, 1, 2, 12",
        "1, 10, 9, 10, 20",
        "1, 10, 10, 1, 11",
        "1, 10, 19, 10, 20",
    )
    fun `player move`(space: Int, score: Long, times: Int, expectedSpace: Int, expectedScore: Long) {
        val player = Player(space, score)

        val result = player.move(times)

        assertThat(result).all {
            prop(Player::space).isEqualTo(expectedSpace)
            prop(Player::score).isEqualTo(expectedScore)
        }
    }
}
