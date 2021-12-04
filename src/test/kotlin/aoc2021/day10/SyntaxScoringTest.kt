package aoc2021.day10

import aoc2021.PuzzleTest

class SyntaxScoringTest : PuzzleTest<Sequence<String>, Long>(
    puzzle = SyntaxScoring,
    path = "examples/day-10.txt",
    part1 = 26397,
    part2 = 288957,
)
