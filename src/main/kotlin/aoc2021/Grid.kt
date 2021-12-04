package aoc2021

abstract class Grid<CELL>(input: List<String>, connectivity: Connectivity) {

    val grid: List<List<CELL>> = input.mapIndexed { y, line ->
        line.toList().mapIndexed { x, value -> toCell(x, y, value.digitToInt()) }
    }
    val list: List<CELL> = grid.flatMap { it.toList() }

    val height = grid.size
    val width = grid.firstOrNull()?.size ?: 0

    abstract fun toCell(x: Int, y: Int, value: Int): CELL

    private val neighbourVectors: List<Pair<Int, Int>> =
        (0 until 9).filter {
            connectivity.applicable(it)
        }.map {
            val vy = it / 3 - 1
            val vx = it % 3 - 1
            vx to vy
        }

    fun neighbours(x: Int, y: Int): List<CELL> =
        neighbourVectors.map { (vx, vy) ->
            x + vx to y + vy
        }.filter { (nx, ny) ->
            nx in 0 until width
                && ny in 0 until height
        }.map { (nx, ny) ->
            grid[ny][nx]
        }

    enum class Connectivity(val applicable: (index: Int) -> Boolean) {
        PERPENDICULAR({ it % 2 == 1 }),
        OMNIDIRECTIONAL({ it != 4 }),
    }
}
