package aoc2021.day10

import aoc2021.StreamPuzzle

/**
 * [Day 10: Syntax Scoring](https://adventofcode.com/2021/day/10)
 */
object SyntaxScoring : StreamPuzzle<String, Long> {

    override val name = "💯 Syntax Scoring"

    override fun parseInput(input: String): String = input

    private val openChars = setOf('(', '[', '{', '<')
    private val charPairs = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )
    private val syntaxCheckerScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )
    private val autocompleteScore = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4,
    )

    /**
     * What is the total syntax error score for those errors?
     */
    override fun part1(input: Sequence<String>): Long = input
        .mapNotNull(this::corruptedChar)
        .mapNotNull(syntaxCheckerScores::get)
        .sum().toLong()

    /**
     * What is the middle score?
     */
    override fun part2(input: Sequence<String>): Long = input
        .mapNotNull(this::autocomplete)
        .map(this::scoreAutocomplete)
        .sorted().toList()
        .let { scores -> scores[scores.size / 2] }

    private fun corruptedChar(line: String): Char? {
        val stack = ArrayDeque<Char>(line.length)
        return line.firstOrNull { corrupted(it, stack) }
    }

    private fun autocomplete(line: String): String? {
        val stack = ArrayDeque<Char>(line.length)
        val corrupted = line.any { corrupted(it, stack) }
        return if (corrupted || stack.isEmpty()) {
            null
        } else {
            stack.reversed().mapNotNull(charPairs::get).joinToString("")
        }
    }

    private fun corrupted(char: Char, stack: ArrayDeque<Char>): Boolean =
        if (char in openChars) {
            stack.addLast(char)
            false
        } else {
            val last = stack.lastOrNull()
            if (charPairs[last] == char) {
                stack.removeLast()
                false
            } else {
                true
            }
        }

    private fun scoreAutocomplete(autocomplete: String): Long {
        var totalScore = 0L
        autocomplete.forEach {
            totalScore *= 5
            totalScore += autocompleteScore[it]!!
        }
        return totalScore
    }
}
