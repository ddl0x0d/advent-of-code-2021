package aoc2021.day20

import aoc2021.BatchPuzzle

/**
 * [Day 20: Trench Map](https://adventofcode.com/2021/day/20)
 */
object TrenchMap : BatchPuzzle<Image, Int> {

    override val name = "🧩 Trench Map"

    override fun parseInput(input: List<String>) = Image(
        algorithm = input.first().map {
            Pixel.bySymbol(it)
        }.toTypedArray(),
        image = input.drop(2).map { line ->
            line.toList().map {
                Pixel.bySymbol(it)
            }.toTypedArray()
        }.toTypedArray()
    )

    /**
     * How many pixels are lit in the resulting image?
     */
    override fun part1(input: Image): Int =
        enhance(input, 2).pixels(Pixel.LIGHT)

    /**
     * How many pixels are lit in the resulting image?
     */
    override fun part2(input: Image): Int =
        enhance(input, 50).pixels(Pixel.LIGHT)

    private fun enhance(input: Image, times: Int): Image =
        generateSequence(input) {
            it.enhance()
        }.drop(times).first()
}

private val vectors3x3: List<Pair<Int, Int>> =
    (-1..1).flatMap { vy ->
        (-1..1).map { vx ->
            vx to vy
        }
    }

class Image(
    private val algorithm: Array<Pixel>,
    private val image: Array<Array<Pixel>>,
    private val background: Pixel = Pixel.DARK,
) {
    private val height = image.size
    private val width = image.firstOrNull()?.size ?: 0

    fun enhance(): Image = Image(
        algorithm = algorithm,
        image = Array(height + 4) { y ->
            Array(width + 4) { x ->
                enhancedPixel(x, y)
            }
        },
        background = algorithm["${background.bit}".repeat(9).toInt(2)]
    )

    private fun enhancedPixel(x: Int, y: Int): Pixel =
        vectors3x3.map { (vx, vy) ->
            (x + vx - 2) to (y + vy - 2)
        }.map { (x, y) ->
            image.getOrNull(y)?.getOrNull(x) ?: background
        }.joinToString("") {
            "${it.bit}"
        }.toInt(2).let {
            algorithm[it]
        }

    fun pixels(pixel: Pixel): Int = image.sumOf { line ->
        line.count { it == pixel }
    }

    override fun toString() = buildString {
        image.forEach {
            append(it.joinToString("") { pixel ->
                "${pixel.symbol}"
            })
            append('\n')
        }
    }
}

enum class Pixel(val symbol: Char, val bit: Char) {
    LIGHT('#', '1'),
    DARK('.', '0');

    companion object {
        fun bySymbol(symbol: Char) = values().firstOrNull {
            it.symbol == symbol
        } ?: error("Unknown symbol: '$symbol'")
    }
}
