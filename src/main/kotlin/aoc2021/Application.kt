package aoc2021

import aoc2021.day01.SonarSweep
import aoc2021.day02.Dive
import aoc2021.day03.BinaryDiagnostic
import aoc2021.day04.GiantSquid
import aoc2021.day05.HydrothermalVenture
import aoc2021.day06.Lanternfish
import aoc2021.day07.TheTreacheryOfWhales
import aoc2021.day08.SevenSegmentSearch
import aoc2021.day09.SmokeBasin
import aoc2021.day10.SyntaxScoring
import aoc2021.day11.DumboOctopus
import aoc2021.day12.PassagePathing
import aoc2021.day13.TransparentOrigami
import aoc2021.day14.ExtendedPolymerization
import aoc2021.day15.Chiton
import aoc2021.day16.PacketDecoder
import aoc2021.day17.TrickShot
import aoc2021.day18.Snailfish
import aoc2021.day19.BeaconScanner
import aoc2021.day20.TrenchMap
import aoc2021.day21.DiracDice
import aoc2021.day22.ReactorReboot
import aoc2021.day23.Amphipods
import aoc2021.day24.ArithmeticLogicUnit
import aoc2021.day25.SeaCucumber
import java.io.File
import kotlin.system.exitProcess

private val puzzles = listOf(
    SonarSweep, Dive, BinaryDiagnostic, GiantSquid, HydrothermalVenture,
    Lanternfish, TheTreacheryOfWhales, SevenSegmentSearch, SmokeBasin, SyntaxScoring,
    DumboOctopus, PassagePathing, TransparentOrigami, ExtendedPolymerization, Chiton,
    PacketDecoder, TrickShot, Snailfish, BeaconScanner, TrenchMap,
    DiracDice, ReactorReboot, Amphipods, ArithmeticLogicUnit, SeaCucumber,
)

private val DEFAULT_DAY = puzzles.size
private const val DEFAULT_INPUT = "input.txt"

fun main(args: Array<String>) {
    println("🎄 Advent of Code 2021 🎄\n")
    val input = getPuzzleInput(args)
    validate(input)
    solvePuzzle(input)
}

private fun getPuzzleInput(args: Array<String>): PuzzleInput =
    if (args.isEmpty()) {
        readPuzzleInput()
    } else {
        parsePuzzleInput(args)
    }

private fun readPuzzleInput() = PuzzleInput(
    day = promptInput("Please enter day from 1 to $DEFAULT_DAY ($DEFAULT_DAY): ") {
        if (it.isBlank()) DEFAULT_DAY else it.toIntOrNull() ?: 0
    },
    path = promptInput("Please enter input file (input.txt): ") {
        it.ifBlank { DEFAULT_INPUT }
    }
).also { println() }

private fun <T> promptInput(message: String, parse: (String) -> T): T {
    print(message)
    val input = readlnOrNull() ?: ""
    return parse(input)
}

private fun parsePuzzleInput(args: Array<String>) = PuzzleInput(
    args[0].toIntOrNull() ?: DEFAULT_DAY,
    args.getOrElse(1) { DEFAULT_INPUT }
)

private fun validate(input: PuzzleInput) {
    try {
        require(input.day in (1..DEFAULT_DAY)) { "Day must be an integer from 1 to $DEFAULT_DAY" }
        require(File(input.path).exists()) { "Input file must exist" }
    } catch (e: Exception) {
        println(e.message)
        exitProcess(1)
    }
}

private fun solvePuzzle(input: PuzzleInput) {
    val (day, path) = input
    val puzzle = puzzles[day - 1]
    puzzle.solve(path)
}

data class PuzzleInput(val day: Int, val path: String)
