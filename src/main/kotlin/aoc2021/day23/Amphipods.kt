package aoc2021.day23

import aoc2021.BatchPuzzle
import aoc2021.readLines

fun main() {
    Amphipods.solve("examples/day-23.txt")
    // expected output: 12_521 and 44_169
}

/**
 * [Day 23: Amphipod](https://adventofcode.com/2021/day/23)
 */
object Amphipods : BatchPuzzle<Burrow, Int> {

    override val name = "🦐 Amphipod"

    private const val HALLWAY_SIZE = 11
    private const val ROOMS = 4

    override fun solve(path: String) {
        println(name)
        val input1 = readLines(path).drop(2).take(2).map { line ->
            line.trim().filterNot { it == '#' }
        }
        println("Part One = ${part1(parseInput(input1))}")
        val input2 = listOf(input1[0], "DCBA", "DBAC", input1[1])
        println("Part Two = ${part2(parseInput(input2))}")
    }

    override fun parseInput(input: List<String>): Burrow {
        val hallway = buildHallway()
        val rooms = buildRooms(input)
        connectLocations(hallway, rooms)
        val amphipods = buildAmphipods(rooms)
        return Burrow(rooms, hallway, amphipods)
    }

    private fun buildHallway() = List(HALLWAY_SIZE) {
        Location(x = it, y = 0)
    }

    private fun buildRooms(input: List<String>) = (0 until ROOMS).map { xi ->
        val x = (xi + 1) * 2
        Room(
            index = x,
            type = Amphipod.Type.values()[xi],
            locations = input.indices.map { yi ->
                Location(
                    x = x, y = yi + 1,
                    amphipod = Amphipod.Type.byChar(input[yi][xi]))
            }
        )
    }

    private fun connectLocations(hallway: List<Location>, rooms: List<Room>) {
        val roomIndexes = rooms.map { it.index }
        hallway.filterNot {
            it.x in roomIndexes
        }.forEach { hall ->
            rooms.forEach { room ->
                val transit = when {
                    hall.x < room.index -> (hall.x + 1)..room.index
                    hall.x > room.index -> (hall.x - 1) downTo room.index
                    else -> error("Hallway and room indexes must be different")
                }.map { hallway[it] }
                (room.locations.size downTo 1).forEach {
                    hall.paths += Path(transit + room.locations.subList(0, it))
                }
                room.locations.forEachIndexed { i, location ->
                    location.paths += Path(room.locations.subList(0, i).reversed() + transit.reversed() + hall)
                }
            }
        }
    }

    private fun buildAmphipods(rooms: List<Room>) = rooms
        .flatMap { it.locations }
        .map { Amphipod(type = it.amphipod!!, location = it) }

    /**
     * What is the least energy required to organize the amphipods?
     */
    override fun part1(input: Burrow): Int = input.complete()!!

    /**
     * What is the least energy required to organize the amphipods?
     */
    override fun part2(input: Burrow): Int = input.complete()!!
}

class Burrow(
    private val rooms: List<Room>,
    private val hallway: List<Location>,
    private val amphipods: List<Amphipod>,
) {
    private val completed: Boolean
        get() = rooms.all { it.completed }

    private var minEnergy = Int.MAX_VALUE

    fun complete(energy: Int = 0, memo: MutableMap<String, MemoState> = mutableMapOf()): Int? {
        val memoKey = memoKey()
        val state = memo[memoKey]
        return when {
            state != null && energy >= state.energy -> state.minEnergy
            energy >= minEnergy -> null
            completed -> energy.also {
                minEnergy = minOf(minEnergy, energy)
            }
            else -> amphipods.flatMap { amphipod ->
                amphipod.possiblePaths().map { path ->
                    Move(amphipod, amphipod.location, path.target, path.length)
                }
            }.mapNotNull { move ->
                val spent = move.forward()
                complete(energy + spent, memo).also {
                    move.backward()
                }
            }.minOrNull().also {
                memo[memoKey] = MemoState(minEnergy = it, energy = energy)
            }
        }
    }

    private fun memoKey(): String = amphipods.joinToString { "${it.location.x}:${it.location.y}" }

    override fun toString() = buildString {
        appendLine("#".repeat(13))
        appendLine("#${hallway.joinToString("")}#")
        appendLine("###${rooms.map { it.locations[0] }.joinToString("#")}###")
        (1 until (rooms.firstOrNull()?.locations?.size ?: 0)).forEach { roomDepth ->
            appendLine("  #${rooms.map { it.locations[roomDepth] }.joinToString("#")}#")
        }
        appendLine("  ${"#".repeat(9)}")
    }
}

data class MemoState(
    val minEnergy: Int?,
    val energy: Int,
)

data class Move(
    val amphipod: Amphipod,
    val from: Location,
    val to: Location,
    val steps: Int,
) {
    fun forward(): Int = amphipod.move(to, steps)
    fun backward(): Int = amphipod.move(from, -steps)
}

data class Amphipod(
    val type: Type,
    var location: Location,
) {
    fun possiblePaths(): List<Path> = with(location) {
        when {
            room == null -> paths.filter {
                it.target.room!!.fits(type) && it.free
            }
            room!!.fits(type) -> emptyList()
            else -> paths.filter { it.free }
        }
    }

    fun move(target: Location, steps: Int): Int {
        location.amphipod = null
        target.amphipod = type
        location = target
        return type.energy * steps
    }

    enum class Type(val char: Char, val energy: Int) {

        AMBER('A', 1),
        BRONZE('B', 10),
        COPPER('C', 100),
        DESERT('D', 1000);

        companion object {
            fun byChar(char: Char): Type =
                values().firstOrNull { it.char == char }
                    ?: error("Unknown character '$char'")
        }
    }
}

data class Room(
    val index: Int,
    val type: Amphipod.Type,
    val locations: List<Location>,
) {
    init {
        locations.forEach { it.room = this }
    }

    fun fits(type: Amphipod.Type): Boolean =
        this.type == type && locations.all { it.amphipod == type || it.amphipod == null }

    val completed: Boolean
        get() = locations.all { it.amphipod == type }
}

data class Location(
    val x: Int, val y: Int,
    var amphipod: Amphipod.Type? = null,
) {
    var room: Room? = null
    val paths = mutableListOf<Path>()
    override fun toString() = "${amphipod?.char ?: '.'}"
}

data class Path(private val locations: List<Location>) {
    val target: Location = locations.last()
    val length = locations.size
    val free: Boolean
        get() = locations.all { it.amphipod == null }
}
