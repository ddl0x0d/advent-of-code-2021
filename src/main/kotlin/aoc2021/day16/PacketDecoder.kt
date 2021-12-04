package aoc2021.day16

import aoc2021.BatchPuzzle

/**
 * [Day 16: Packet Decoder](https://adventofcode.com/2021/day/16)
 */
object PacketDecoder : BatchPuzzle<String, Long> {

    override val name = "🔢 Packet Decoder"

    override fun parseInput(input: List<String>) = input.first()

    /**
     * What do you get if you add up the version numbers in all packets?
     */
    override fun part1(input: String): Long = sumOfVersions(decode(input))

    private fun sumOfVersions(packet: Packet): Long = packet.version +
        when (packet) {
            is LiteralValue -> 0
            is Operator -> packet.packets.sumOf { sumOfVersions(it) }
        }

    /**
     * What do you get if you evaluate the expression represented
     * by your hexadecimal-encoded BITS transmission?
     */
    override fun part2(input: String): Long = decode(input).value

    private fun decode(hexadecimal: String): Packet = Decoder(hexadecimal).decodePacket()
}

class Decoder(hexadecimal: String) {

    private var position = 0
    private val binary: String = hexadecimal.toList().joinToString("") {
        it.digitToInt(16).toString(2).padStart(4, '0')
    }

    fun decodePacket(): Packet {
        val version = decodeInt(VERSION_BITS)
        val typeId = decodeInt(TYPE_ID_BITS)
        return when (typeId) {
            LiteralValue.TYPE_ID -> decodeLiteralValue(version)
            else -> decodeOperator(version, typeId)
        }
    }

    private fun decodeLiteralValue(version: Int): LiteralValue {
        var value = ""
        do {
            val group = decodeString(LITERAL_GROUP_BITS)
            value += group.substring(1)
            val last = group[0] == '0'
        } while (!last)
        return LiteralValue(version, value.toLong(2))
    }

    private fun decodeOperator(version: Int, typeId: Int): Operator {
        val lengthTypeId = decodeInt(LENGTH_TYPE_ID_BITS)
        val lengthType = Operator.LengthType.getById(lengthTypeId)
        val length = decodeInt(lengthType.bits)
        val finish = position + length
        val packets = when (lengthType) {
            Operator.LengthType.BITS -> generateSequence {
                takeIf { position < finish }
            }.map { decodePacket() }.toList()
            Operator.LengthType.PACKETS -> (1..length).map { decodePacket() }
        }
        return Operator(version, typeId, packets)
    }

    private fun decodeInt(bits: Int): Int = decodeString(bits).toInt(2)

    private fun decodeString(bits: Int): String = binary
        .substring(position, position + bits)
        .also { position += bits }

    companion object {
        const val VERSION_BITS = 3
        const val TYPE_ID_BITS = 3
        const val LITERAL_GROUP_BITS = 5
        const val LENGTH_TYPE_ID_BITS = 1
    }
}

sealed interface Packet {
    val version: Int
    val value: Long
}

data class LiteralValue(
    override val version: Int,
    override val value: Long
) : Packet {
    companion object {
        const val TYPE_ID = 4
    }
}

data class Operator(override val version: Int, val typeId: Int, val packets: List<Packet>) : Packet {

    private val operation = Operation.values().first { it.typeId == typeId }

    override val value = operation(packets)

    enum class LengthType(private val id: Int, val bits: Int) {
        BITS(0, 15), PACKETS(1, 11);

        companion object {
            fun getById(id: Int): LengthType = values().first { it.id == id }
        }
    }

    private enum class Operation(val typeId: Int, private val op: (List<Packet>) -> Long) {

        SUM(0, op = { packets -> packets.sumOf { it.value } }),
        PRODUCT(1, op = { it.fold(1) { acc, packet -> acc * packet.value } }),
        MINIMUM(2, op = { packets -> packets.minByOrNull { it.value }?.value ?: 0 }),
        MAXIMUM(3, op = { packets -> packets.maxByOrNull { it.value }?.value ?: 0 }),
        GREATER_THAN(5, op = { (a, b) -> if (a.value > b.value) 1 else 0 }),
        LESS_THAN(6, op = { (a, b) -> if (a.value < b.value) 1 else 0 }),
        EQUAL_TO(7, op = { (a, b) -> if (a.value == b.value) 1 else 0 });

        operator fun invoke(packets: List<Packet>): Long = op(packets)
    }
}
