package aoc2021.day02

import aoc2021.StreamPuzzle

/**
 * [Day 2: Dive!](https://adventofcode.com/2021/day/2)
 */
object Dive : StreamPuzzle<Command, Int> {

    override val name = "🌊 Dive!"

    override fun parseInput(input: String): Command {
        val (type, x) = input.split(" ", limit = 2)
        return Command(Command.Type.valueOf(type.uppercase()), x.toInt())
    }

    /**
     * What do you get if you multiply your final
     * horizontal position by your final depth?
     */
    override fun part1(input: Sequence<Command>): Int {
        var position = 0
        var depth = 0
        input.forEach {
            when (it.type) {
                Command.Type.FORWARD -> position += it.x
                Command.Type.DOWN -> depth += it.x
                Command.Type.UP -> depth -= it.x
            }
        }
        return position * depth
    }

    /**
     * What do you get if you multiply your final
     * horizontal position by your final depth?
     */
    override fun part2(input: Sequence<Command>): Int {
        var position = 0
        var depth = 0
        var aim = 0
        input.forEach {
            when (it.type) {
                Command.Type.FORWARD -> {
                    position += it.x
                    depth += aim * it.x
                }
                Command.Type.DOWN -> aim += it.x
                Command.Type.UP -> aim -= it.x
            }
        }
        return position * depth
    }
}

data class Command(val type: Type, val x: Int) {
    enum class Type {
        FORWARD, DOWN, UP
    }
}
