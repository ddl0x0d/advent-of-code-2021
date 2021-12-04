package aoc2021.day14

import aoc2021.PuzzleTest

class ExtendedPolymerizationTest : PuzzleTest<Instruction, Long>(
    puzzle = ExtendedPolymerization,
    path = "examples/day-14.txt",
    part1 = 1_588L,
    part2 = 2_188_189_693_529L,
)
