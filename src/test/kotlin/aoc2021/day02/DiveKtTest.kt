package aoc2021.day02

import aoc2021.PuzzleTest

class DiveKtTest : PuzzleTest<Sequence<Command>, Int>(
    puzzle = Dive,
    path = "examples/day-02.txt",
    part1 = 150,
    part2 = 900,
)
