package aoc2021.day01

import aoc2021.PuzzleTest

class SonarSweepKtTest : PuzzleTest<Sequence<Int>, Int>(
    puzzle = SonarSweep,
    path = "examples/day-01.txt",
    part1 = 7,
    part2 = 5,
)
