package aoc2021.day13

import aoc2021.BatchPuzzle

/**
 * [Day 13: Transparent Origami](https://adventofcode.com/2021/day/13)
 */
object TransparentOrigami : BatchPuzzle<Origami, Int> {

    override val name = "📂 Transparent Origami"

    private val regex = "fold along ([xy])=(\\d+)".toRegex()

    override fun solve(path: String) {
        println(name)
        println("Part One = ${solve(path, this::part1)}")
        println("Part Two = see below\n")
        solve(path, this::part2)
    }

    override fun parseInput(input: List<String>): Origami {
        val separatorIndex = input.indexOfFirst { it.isEmpty() }
        return Origami(
            dots = input.take(separatorIndex)
                .map {
                    val (x, y) = it.split(",", limit = 2)
                    Dot(x = x.toInt(), y = y.toInt())
                }.toSet(),
            folds = input.drop(separatorIndex + 1)
                .mapNotNull {
                    regex.matchEntire(it)?.destructured
                }.map { (type, value) ->
                    Fold(
                        type = when (type) {
                            "x" -> Fold.Type.RIGHT_TO_LEFT
                            "y" -> Fold.Type.BOTTOM_TO_TOP
                            else -> error("unknown fold type '$type'")
                        },
                        coordinate = value.toInt()
                    )
                })
    }

    /**
     * How many dots are visible after completing just the
     * first fold instruction on your transparent paper?
     */
    override fun part1(input: Origami): Int = input.fold().dots.size

    /**
     * What code do you use to activate the infrared thermal imaging camera system?
     */
    override fun part2(input: Origami): Int {
        var result = input
        repeat(input.folds.size) {
            result = result.fold()
        }
        println(result)
        return 0
    }
}

data class Dot(val x: Int, val y: Int)

data class Fold(val type: Type, val coordinate: Int) {
    enum class Type {
        RIGHT_TO_LEFT, BOTTOM_TO_TOP
    }
}

class Origami(
    val dots: Set<Dot>,
    val folds: List<Fold>,
) {
    fun fold(): Origami {
        val (type, value) = folds.first()
        return Origami(
            dots = when (type) {
                Fold.Type.RIGHT_TO_LEFT -> {
                    val (left, right) = dots.partition { it.x < value }
                    val folded = right.map { (x, y) -> Dot(value - (x - value), y) }
                    left + folded
                }
                Fold.Type.BOTTOM_TO_TOP -> {
                    val (top, bottom) = dots.partition { it.y < value }
                    val folded = bottom.map { (x, y) -> Dot(x, value - (y - value)) }
                    top + folded
                }
            }.toSet(),
            folds = folds.drop(1)
        )
    }

    override fun toString(): String {
        val maxX = dots.maxOf { it.x }
        val maxY = dots.maxOf { it.y }
        val paper = Array(maxY + 1) {
            Array(maxX + 1) { '.' }
        }
        dots.forEach { (x, y) -> paper[y][x] = '#' }
        return paper.joinToString(separator = "\n") { it.joinToString("") }
    }
}
