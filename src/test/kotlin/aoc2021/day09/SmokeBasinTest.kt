package aoc2021.day09

import aoc2021.PuzzleTest

class SmokeBasinTest : PuzzleTest<HeightMap, Int>(
    puzzle = SmokeBasin,
    path = "examples/day-09.txt",
    part1 = 15,
    part2 = 1134,
)
