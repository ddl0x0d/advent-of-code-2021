package aoc2021.day12

import aoc2021.PuzzleTest

class PassagePathing1Test : PuzzleTest<CaveSystem, Int>(
    puzzle = PassagePathing,
    path = "examples/day-12-1.txt",
    part1 = 10,
    part2 = 36,
)

class PassagePathing2Test : PuzzleTest<CaveSystem, Int>(
    puzzle = PassagePathing,
    path = "examples/day-12-2.txt",
    part1 = 19,
    part2 = 103,
)

class PassagePathing3Test : PuzzleTest<CaveSystem, Int>(
    puzzle = PassagePathing,
    path = "examples/day-12-3.txt",
    part1 = 226,
    part2 = 3509,
)
