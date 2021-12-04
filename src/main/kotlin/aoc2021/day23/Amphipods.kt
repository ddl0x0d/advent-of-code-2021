package aoc2021.day23

import aoc2021.BatchPuzzle

/**
 * [Day 23: Amphipod](https://adventofcode.com/2021/day/23)
 */
object Amphipods : BatchPuzzle<Burrow, Int> {

    override val name = "🦐 Amphipod"

    private const val HALLWAY_SIZE = 11
    private val roomXs = 3..9 step 2
    private const val OUTER_ROOM_Y = 2
    private const val INNER_ROOM_Y = 3

    override fun parseInput(input: List<String>): Burrow {
        val hallway = buildHallway()
        val rooms = buildRooms(input)
        connectLocations(hallway, rooms)
        val amphipods = buildAmphipods(rooms)
        return Burrow(rooms, hallway, amphipods)
    }

    private fun buildHallway() = List(HALLWAY_SIZE) {
        Location(index = it)
    }

    private fun buildRooms(input: List<String>) = roomXs.mapIndexed { i, x ->
        Room(
            type = Amphipod.Type.values()[i],
            outer = Location(index = (i + 1) * 2, amphipod = Amphipod.Type.byChar(input[OUTER_ROOM_Y][x])),
            inner = Location(index = (i + 1) * 2, amphipod = Amphipod.Type.byChar(input[INNER_ROOM_Y][x]))
        )
    }

    private fun connectLocations(hallway: List<Location>, rooms: List<Room>) {
        val roomIndexes = rooms.map { it.inner.index }
        hallway.filterNot {
            it.index in roomIndexes
        }.forEach { hallwayLocation ->
            rooms.forEach { room ->
                val hallwayIndex = hallwayLocation.index
                val roomIndex = room.inner.index
                val indexRange = when {
                    hallwayIndex < roomIndex -> (hallwayIndex + 1)..roomIndex
                    hallwayIndex > roomIndex -> (hallwayIndex - 1) downTo roomIndex
                    else -> error("Hallway and room indexes must be different")
                }
                val transit = indexRange.map { hallway[it] }
                hallwayLocation.paths += Path(transit + room.outer + room.inner)
                hallwayLocation.paths += Path(transit + room.outer)
                room.inner.paths += Path(listOf(room.outer) + transit.reversed() + hallwayLocation)
                room.outer.paths += Path(transit.reversed() + hallwayLocation)
            }
        }
    }

    private fun buildAmphipods(rooms: List<Room>) = rooms
        .flatMap { listOf(it.outer, it.inner) }
        .map { Amphipod(type = it.amphipod!!, location = it) }

    /**
     * What is the least energy required to organize the amphipods?
     */
    override fun part1(input: Burrow): Int = input.explore()!!

    /**
     * TODO
     */
    override fun part2(input: Burrow): Int {
        return 0
    }
}

class Burrow(
    private val rooms: List<Room>,
    private val hallway: List<Location>,
    private val amphipods: List<Amphipod>,
) {
    private val organized: Boolean
        get() = rooms.all { it.organized }

    private var minEnergy = Int.MAX_VALUE

    fun explore(energy: Int = 0): Int? = when {
        energy >= minEnergy -> null
        organized -> energy.also {
            minEnergy = minOf(minEnergy, energy)
            println(minEnergy)
        }
        else -> amphipods.flatMap { amphipod ->
            amphipod.possiblePaths().map { path ->
                amphipod to path
            }
        }.mapNotNull { (amphipod, path) ->
            val start = amphipod.location
            val spent = amphipod.move(path.target, path.length)
            explore(energy + spent).also {
                amphipod.move(start, -path.length)
            }
        }.minOrNull()
    }

    override fun toString() = buildString {
        appendLine("#".repeat(13))
        appendLine("#${hallway.joinToString("")}#")
        appendLine("###${rooms.map { it.outer }.joinToString("#")}###")
        appendLine("  #${rooms.map { it.inner }.joinToString("#")}#")
        appendLine("  ${"#".repeat(9)}")
    }
}

data class Amphipod(
    val type: Type,
    var location: Location,
) {
    fun possiblePaths(): List<Path> = with(location) {
        when {
            room == null -> paths.filter { it.target.room?.type == type && it.free }
            type == room?.type && location === room?.inner -> emptyList()
            type == room?.type && type == room?.outer?.amphipod -> emptyList()
            else -> paths.filter { it.free }
        }
    }

    fun move(target: Location, size: Int): Int {
        location.amphipod = null
        target.amphipod = type
        location = target
        return type.energy * size
    }

    enum class Type(val char: Char, val energy: Int) {

        AMBER('A', 1),
        BRONZE('B', 10),
        COPPER('C', 100),
        DESERT('D', 1000);

        companion object {
            fun byChar(char: Char): Type =
                values().first { it.char == char }
        }
    }
}

data class Room(
    val type: Amphipod.Type,
    val outer: Location,
    val inner: Location,
) {
    init {
        outer.room = this
        inner.room = this
    }

    val organized: Boolean
        get() = type == outer.amphipod && type == inner.amphipod
}

data class Location(
    val index: Int,
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
