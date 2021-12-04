package aoc2021.day04

import aoc2021.BatchPuzzle

/**
 * [Day 4: Giant Squid](https://adventofcode.com/2021/day/4)
 */
object GiantSquid : BatchPuzzle<Bingo, Int> {

    override val name = "🦑 Giant Squid"

    private val whitespaces = " +".toRegex()

    override fun parseInput(input: List<String>) = Bingo(
        numbers = input.first().split(",").map { it.toInt() },
        boards = input
            .drop(1)
            .filter { it.isNotEmpty() }
            .flatMap { it.trim().split(whitespaces) }
            .map { it.toInt() }
            .chunked(Board.SIZE * Board.SIZE)
            .map { Board(it) }
            .toList()
    )

    /**
     * What will your final score be if you choose that board?
     */
    override fun part1(input: Bingo): Int {
        val (numbers, boards) = input
        val last = numbers.asSequence().takeWhile {
            boards.none { it.won }
        }.onEach { number ->
            boards.forEach { it.draw(number) }
        }.last()
        return boards.first { it.won }.score * last
    }

    /**
     * Once it wins, what would its final score be?
     */
    override fun part2(input: Bingo): Int {
        val (numbers, boards) = input
        val last = numbers.asSequence().takeWhile {
            boards.any { !it.won }
        }.onEach { number ->
            boards.forEach { it.draw(number) }
        }.last()
        return boards.first { it.lastNumber == last }.score * last
    }
}

data class Bingo(
    val numbers: List<Int>,
    val boards: List<Board>,
)

class Board(numbers: List<Int>) {
    private val all: List<Cell>
    private val rows: List<Line>
    private val cols: List<Line>

    var won = false
        private set

    var lastNumber: Int? = null
        private set

    val score: Int
        get() = all.filter { !it.marked }.sumOf { it.number }

    init {
        all = numbers.map { Cell(it) }
        rows = all.chunked(SIZE).map { Line(it) }
        cols = (0 until SIZE).map { index ->
            rows.map { it.cells[index] }
        }.map { Line(it) }
    }

    fun draw(number: Int) {
        if (!won) {
            lastNumber = number
            all.filter { it.number == number }.forEach { it.mark() }
            won = rows.any { it.completed } || cols.any { it.completed }
        }
    }

    fun contains(number: Int): Boolean = all.any { it.number == number }

    companion object {
        const val SIZE = 5
    }
}

private data class Cell(val number: Int) {
    var marked: Boolean = false
        private set

    fun mark() {
        marked = true
    }
}

private data class Line(val cells: List<Cell>) {
    val completed: Boolean
        get() = cells.all { it.marked }
}
