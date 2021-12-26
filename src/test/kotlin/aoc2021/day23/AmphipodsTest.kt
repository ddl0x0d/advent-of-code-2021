package aoc2021.day23

import aoc2021.PuzzleTest
import org.junit.jupiter.api.Disabled

@Disabled
class AmphipodsTest : PuzzleTest<Burrow, Int>(
    puzzle = Amphipods,
    path = "examples/day-23.txt",
    part1 = 12_521,
    part2 = 44_169,
)
