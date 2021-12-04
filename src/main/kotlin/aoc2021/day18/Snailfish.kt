@file:Suppress("NON_EXHAUSTIVE_WHEN_STATEMENT")

package aoc2021.day18

import aoc2021.StreamPuzzle
import kotlin.math.ceil
import kotlin.math.floor

/**
 * [Day 18: Snailfish](https://adventofcode.com/2021/day/18)
 */
object Snailfish : StreamPuzzle<SnailNumber, Long> {

    override val name = "🐌 Snailfish"

    override fun parseInput(input: String): SnailNumber =
        input.fold(ArrayDeque<SnailNumber>()) { numbers, char ->
            numbers.apply {
                when {
                    char == ']' -> {
                        val num2 = removeLast()
                        val num1 = removeLast()
                        addLast(SnailPair(num1, num2))
                    }
                    char.isDigit() -> addLast(SnailRegular(char.digitToInt()))
                }
            }
        }.last()

    /**
     * What is the magnitude of the final sum?
     */
    override fun part1(input: Sequence<SnailNumber>): Long =
        input.reduce { acc, number -> (acc + number) }.magnitude

    /**
     * What is the largest magnitude of any sum of two different
     * snailfish numbers from the homework assignment?
     */
    override fun part2(input: Sequence<SnailNumber>): Long {
        val numbers = input.toList()
        return numbers.indices.flatMap { a ->
            numbers.indices.map { b ->
                a to b
            }
        }.filterNot { (a, b) ->
            a == b
        }.map { (a, b) ->
            numbers[a].copy() + numbers[b].copy()
        }.maxOf {
            it.magnitude
        }
    }
}

sealed interface SnailNumber {

    val magnitude: Long

    operator fun plus(that: SnailNumber) = SnailPair(this, that).apply { reduce() }

    fun reduce() {
        while (true) {
            if (explode() != NoExplosion) {
                continue
            }
            if (split() == WasSplit) {
                continue
            }
            break
        }
    }

    /**
     * If any pair is nested inside four pairs, the leftmost such pair explodes
     */
    fun explode(depth: Int = 0): Explosion

    fun implode(explosion: DirectedExplosion)

    /**
     * If any regular number is 10 or greater, the leftmost such regular number splits
     */
    fun split(): Split

    fun copy() = Snailfish.parseInput(toString())
}

data class SnailRegular(var number: Int) : SnailNumber {
    override val magnitude: Long
        get() = number.toLong()

    override fun explode(depth: Int) = NoExplosion

    override fun implode(explosion: DirectedExplosion) {
        this.number += explosion.number
    }

    override fun split(): Split =
        if (number > 9) {
            val half = number.toDouble() / 2
            SplitPair(
                SnailPair(
                    left = SnailRegular(floor(half).toInt()),
                    right = SnailRegular(ceil(half).toInt())
                )
            )
        } else NoSplit

    override fun toString() = number.toString()
}

data class SnailPair(var left: SnailNumber, var right: SnailNumber) : SnailNumber {
    override val magnitude: Long
        get() = 3 * left.magnitude + 2 * right.magnitude

    private val regular: Boolean
        get() = left is SnailRegular && right is SnailRegular

    override fun explode(depth: Int): Explosion {
        if (depth == 4 && regular) {
            return FullExplosion(
                left = (left as SnailRegular).number,
                right = (right as SnailRegular).number
            )
        }
        when (val explosion = left.explode(depth + 1)) {
            is FullExplosion -> {
                left = SnailRegular(0)
                right.implode(DirectedExplosion(Direction.LEFT, explosion.right))
                return DirectedExplosion(Direction.LEFT, explosion.left)
            }
            is DirectedExplosion -> return when (explosion.direction) {
                Direction.LEFT -> explosion
                Direction.RIGHT -> {
                    right.implode(DirectedExplosion(Direction.LEFT, explosion.number))
                    WasExplosion
                }
            }
            is WasExplosion -> return explosion
        }
        when (val explosion = right.explode(depth + 1)) {
            is FullExplosion -> {
                right = SnailRegular(0)
                left.implode(DirectedExplosion(Direction.RIGHT, explosion.left))
                return DirectedExplosion(Direction.RIGHT, explosion.right)
            }
            is DirectedExplosion -> return when (explosion.direction) {
                Direction.LEFT -> {
                    left.implode(DirectedExplosion(Direction.RIGHT, explosion.number))
                    WasExplosion
                }
                Direction.RIGHT -> explosion
            }
            is WasExplosion -> return explosion
        }
        return NoExplosion
    }

    override fun implode(explosion: DirectedExplosion) {
        explosion.direction.selectFrom(this).implode(explosion)
    }

    override fun split(): Split {
        when (val split = left.split()) {
            is SplitPair -> {
                left = split.pair
                return WasSplit
            }
            is WasSplit -> return WasSplit
        }
        when (val split = right.split()) {
            is SplitPair -> {
                right = split.pair
                return WasSplit
            }
            is WasSplit -> return WasSplit
        }
        return NoSplit
    }

    override fun toString() = "[$left,$right]"
}

sealed interface Explosion
object NoExplosion : Explosion
object WasExplosion : Explosion
data class FullExplosion(val left: Int, val right: Int) : Explosion
data class DirectedExplosion(val direction: Direction, val number: Int) : Explosion

sealed interface Split
object NoSplit : Split
object WasSplit : Split
data class SplitPair(val pair: SnailPair) : Split

enum class Direction(val selectFrom: (SnailPair) -> SnailNumber) {
    LEFT({ it.left }),
    RIGHT({ it.right })
}
