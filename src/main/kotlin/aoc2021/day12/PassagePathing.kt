package aoc2021.day12

import aoc2021.BatchPuzzle

/**
 * [Day 12: Passage Pathing](https://adventofcode.com/2021/day/12)
 */
object PassagePathing : BatchPuzzle<CaveSystem, Int> {

    override val name = "🧭 Passage Pathing"

    override fun parseInput(input: List<String>): CaveSystem {
        val caves = mutableMapOf<String, Cave>()
        input.forEach {
            val (id1, id2) = it.split("-", limit = 2)
            val cave1 = caves.getOrPut(id1) { Cave(id1) }
            val cave2 = caves.getOrPut(id2) { Cave(id2) }
            cave1.connections += cave2
            cave2.connections += cave1
        }
        return CaveSystem(
            start = caves["start"]!!,
            end = caves["end"]!!,
        )
    }

    /**
     * How many paths through this cave system are there that visit small caves at most once?
     */
    override fun part1(input: CaveSystem): Int =
        numPaths(from = Path(input.start), to = input.end) { path, cave ->
            cave.type == Cave.Type.BIG || cave !in path.visited
        }

    /**
     * How many paths through this cave system are there?
     */
    override fun part2(input: CaveSystem): Int =
        numPaths(from = Path(input.start), to = input.end) { path, cave ->
            cave.type == Cave.Type.BIG
                || cave !in path.visited
                || !path.smallCaveVisitedTwice
                && cave != input.start
                && cave != input.end
        }

    private fun numPaths(from: Path, to: Cave, canVisit: (Path, Cave) -> Boolean): Int =
        if (from.cave == to) {
            1
        } else from.cave.connections.filter {
            canVisit(from, it)
        }.map(from::visit).sumOf {
            numPaths(it, to, canVisit)
        }
}

data class CaveSystem(
    val start: Cave,
    val end: Cave,
)

data class Cave(val id: String) {
    val connections = mutableListOf<Cave>()
    val type = if (id[0].isUpperCase()) Type.BIG else Type.SMALL

    enum class Type {
        SMALL, BIG
    }
}

data class Path(
    val cave: Cave,
    val visited: Set<Cave> = setOf(cave),
    val smallCaveVisitedTwice: Boolean = false,
) {
    fun visit(cave: Cave) = Path(
        cave = cave,
        visited = visited + cave,
        smallCaveVisitedTwice = smallCaveVisitedTwice
            || cave.type == Cave.Type.SMALL
            && cave in visited
    )
}
