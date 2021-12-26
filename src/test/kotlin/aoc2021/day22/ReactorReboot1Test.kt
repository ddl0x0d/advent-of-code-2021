package aoc2021.day22

import aoc2021.PuzzleTest
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

class ReactorReboot1Test : PuzzleTest<Sequence<RebootStep>, Long>(
    puzzle = ReactorReboot,
    path = "examples/day-22-1.txt",
    part1 = 39,
    part2 = 39,
)

class ReactorReboot2Test : PuzzleTest<Sequence<RebootStep>, Long>(
    puzzle = ReactorReboot,
    path = "examples/day-22-2.txt",
    part1 = 590_784,
    part2 = 39_769_202_357_779,
)

class ReactorReboot3Test : PuzzleTest<Sequence<RebootStep>, Long>(
    puzzle = ReactorReboot,
    path = "examples/day-22-3.txt",
    part1 = 474_140,
    part2 = 2_758_514_936_282_235,
)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReactorRebootTest {

    @ParameterizedTest
    @CsvSource(
        "  0,  0,   1,  1,  1,  0",
        "-10, -1,   1, 10,  1,  0",
        "-10,  0,   0, 10,  0,  0",
        "  1, 20,  10, 30, 10, 20",
        "-10, 10,  -5,  5, -5,  5",
        "  1,  1,   1,  1,  1,  1",
        "  1, 10,   1, 10,  1, 10",
    )
    fun `range intersection`(a1: Int, a2: Int, b1: Int, b2: Int, c1: Int, c2: Int) {
        val input1 = a1..a2
        val input2 = b1..b2

        val actual1 = input1 intersection input2
        val actual2 = input2 intersection input1

        val expected = c1..c2
        assertThat(setOf(expected, actual1, actual2)).hasSize(1)
    }

    @ParameterizedTest
    @CsvSource(
        "1,  0, 1, 10, 1, 10,    0",
        "1, 10, 1,  0, 1, 10,    0",
        "1, 10, 1, 10, 1,  0,    0",
        "1,  1, 1, 10, 1, 10,  100",
        "1, 10, 1,  1, 1, 10,  100",
        "1, 10, 1, 10, 1,  1,  100",
        "1, 10, 1, 10, 1, 10, 1000",
    )
    fun `cuboid volume`(x1: Int, x2: Int, y1: Int, y2: Int, z1: Int, z2: Int, expected: Long) {
        val cuboid = Cuboid(x1..x2, y1..y2, z1..z2)

        val actual = cuboid.volume

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("cuboidIntersection")
    fun `cuboid intersection`(a: Cuboid, b: Cuboid, expected: Cuboid) {
        val actual1 = a intersection b
        val actual2 = b intersection a
        assertThat(setOf(expected, actual1, actual2)).hasSize(1)
    }

    private fun cuboidIntersection() = listOf<Arguments>(
        Arguments.of(
            Cuboid(1..1, 1..1, 1..1),
            Cuboid(2..2, 2..2, 2..2),
            Cuboid.EMPTY,
        ),
        Arguments.of(
            Cuboid(1..1, 1..1, 1..1),
            Cuboid(1..1, 1..1, 1..1),
            Cuboid(1..1, 1..1, 1..1),
        ),
        Arguments.of(
            Cuboid(1..2, 1..2, 1..2),
            Cuboid(2..3, 2..3, 2..3),
            Cuboid(2..2, 2..2, 2..2),
        ),
    )
}
