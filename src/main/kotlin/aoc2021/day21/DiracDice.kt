package aoc2021.day21

import aoc2021.BatchPuzzle

/**
 * [Day 21: Dirac Dice](https://adventofcode.com/2021/day/21)
 */
object DiracDice : BatchPuzzle<List<Player>, Long> {

    override val name = "🎲 Dirac Dice"

    private val regex = "Player \\d starting position: (\\d+)".toRegex()

    override fun parseInput(input: List<String>) =
        input.mapNotNull {
            regex.matchEntire(it)
        }.map {
            Player(space = it.groupValues[1].toInt())
        }

    /**
     * What do you get if you multiply the score of the losing player
     * by the number of times the die was rolled during the game?
     */
    override fun part1(input: List<Player>): Long {
        val players = input.toMutableList()
        var playerIndex = 0
        val die = Die()
        while (players.all { it.score < 1_000 }) {
            val player = players[playerIndex]
            val result = (1..3).sumOf {
                die.roll()
                die.value
            }
            players[playerIndex] = player.move(result)
            playerIndex = 1 - playerIndex
        }
        val loser = players.minByOrNull { it.score }!!
        return die.rolls * loser.score
    }

    /**
     * Find the player that wins in more universes;
     * in how many universes does that player win?
     */
    override fun part2(input: List<Player>): Long =
        diracWins(Universe(players = input)).maxOrNull()!!

    private fun diracWins(universe: Universe, combos: Long = 1): List<Long> =
        if (universe.players.any { it.score >= 21 }) {
            List(2) { if (it == universe.playerIndex) 0 else combos }
        } else diracCombos.keys.map { result ->
            diracWins(
                Universe(
                    playerIndex = 1 - universe.playerIndex,
                    players = List(2) {
                        val player = universe.players[it]
                        if (it == universe.playerIndex) player.move(result) else player
                    }
                ),
                combos = combos * diracCombos[result]!!
            )
        }.fold(listOf(0L, 0L)) { acc, wins ->
            listOf(acc[0] + wins[0], acc[1] + wins[1])
        }
}

private val diracCombos = mapOf(
    3 to 1,
    4 to 3,
    5 to 6,
    6 to 7,
    7 to 6,
    8 to 3,
    9 to 1,
)

data class Universe(
    val playerIndex: Int = 0,
    val players: List<Player>,
)

data class Player(val space: Int, val score: Long = 0) {

    fun move(times: Int): Player {
        var newSpace = space + times % SPACES
        if (newSpace > SPACES) {
            newSpace %= SPACES
        }
        return Player(
            space = newSpace,
            score = score + newSpace
        )
    }

    companion object {
        private const val SPACES = 10
    }
}

data class Die(
    var value: Int = 0,
    var rolls: Long = 0,
) {
    fun roll() {
        rolls++
        value++
        if (value > MAX_VALUE) {
            value = 1
        }
    }

    companion object {
        private const val MAX_VALUE = 100
    }
}
