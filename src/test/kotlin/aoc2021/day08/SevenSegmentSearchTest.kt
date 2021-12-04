package aoc2021.day08

import aoc2021.PuzzleTest

class SevenSegmentSearchTest : PuzzleTest<Sequence<Entry>, Int>(
    puzzle = SevenSegmentSearch,
    path = "examples/day-08.txt",
    part1 = 26,
    part2 = 61_229,
)
