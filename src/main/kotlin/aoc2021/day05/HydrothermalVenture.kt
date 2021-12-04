package aoc2021.day05

import aoc2021.BatchPuzzle
import kotlin.math.abs
import kotlin.math.sign

/**
 * [Day 5: Hydrothermal Venture](https://adventofcode.com/2021/day/5)
 */
object HydrothermalVenture : BatchPuzzle<List<Line>, Int> {

    override val name = "🌪 Hydrothermal Venture"

    private val lineRegex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()

    override fun parseInput(input: List<String>): List<Line> = input.mapNotNull {
        lineRegex.matchEntire(it)
    }.map { match ->
        val (x1, y1, x2, y2) = match.destructured.toList().map { it.toInt() }
        Line(Point(x1, y1), Point(x2, y2))
    }

    /**
     * At how many points do at least two lines overlap?
     */
    override fun part1(input: List<Line>): Int {
        val perpendicular = setOf(Line.Type.HORIZONTAL, Line.Type.VERTICAL)
        val lines = input.filter { it.type in perpendicular }
        return solve(lines)
    }

    /**
     * At how many points do at least two lines overlap?
     */
    override fun part2(input: List<Line>): Int {
        val lines = input.filter { it.type != Line.Type.UNKNOWN }
        return solve(lines)
    }

    private fun solve(lines: List<Line>): Int {
        val points = lines.flatMap { (start, end) -> listOf(start, end) }

        val minX = points.minOf { it.x }
        val minY = points.minOf { it.y }
        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }

        val field = Array(maxX - minX + 1) { Array(maxY - minY + 1) { 0 } }
        lines.forEach { line ->
            (0 until line.length).forEach { i ->
                val x = line.start.x - minX + i * line.vector.x
                val y = line.start.y - minY + i * line.vector.y
                field[x][y]++
            }
        }
        return field.flatMap { it.toList() }.count { it > 1 }
    }
}

data class Point(val x: Int, val y: Int)

data class Line(val start: Point, val end: Point) {
    val vector: Point = Point(
        x = (end.x - start.x).sign,
        y = (end.y - start.y).sign,
    )

    enum class Type {
        HORIZONTAL, VERTICAL, DIAGONAL, UNKNOWN
    }

    val type: Type = when {
        start.x == end.x -> Type.VERTICAL
        start.y == end.y -> Type.HORIZONTAL
        abs(start.x - end.x) == abs(start.y - end.y) -> Type.DIAGONAL
        else -> Type.UNKNOWN
    }

    val length: Int = when (type) {
        Type.HORIZONTAL -> (end.x - start.x) * vector.x + 1
        Type.VERTICAL -> (end.y - start.y) * vector.y + 1
        Type.DIAGONAL -> (end.x - start.x) * vector.x + 1
        else -> -1
    }
}
