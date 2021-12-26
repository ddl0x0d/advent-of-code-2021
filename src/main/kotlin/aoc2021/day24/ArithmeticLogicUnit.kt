package aoc2021.day24

import aoc2021.BatchPuzzle
import kotlin.math.min

fun main() {
    ArithmeticLogicUnit.solve("input.txt")
}

/**
 * [Day 24: Arithmetic Logic Unit](https://adventofcode.com/2021/day/24)
 */
object ArithmeticLogicUnit : BatchPuzzle<MONAD, String> {

    override val name = "🧮 Arithmetic Logic Unit"

    override fun parseInput(input: List<String>) = MONAD(input)

    /**
     * What is the largest model number accepted by MONAD?
     */
    override fun part1(input: MONAD): String {
        input.check("9".repeat(14))
        return ""
    }

    /**
     * TODO
     */
    override fun part2(input: MONAD): String {
        return ""
    }
}

class MONAD(private val instructions: List<String>) {

    private var x = Variable("x")
    private var y = Variable("y")
    private var z = Variable("z")
    private var w = Variable("w")

    private val regex = "(\\w{3}) ([xyzw])(?: (([xyzw]|-?\\d+)))?".toRegex()

    fun check(modelNumber: String): Boolean {
        check(modelNumber.length == 14) { "Model number must be 14-digit number" }
        var modelIndex = 0
        instructions.forEach { instruction ->
            val (op, a, b) = regex.matchEntire(instruction)!!.destructured
            val varA = variable(a)!!
            val operation = Operation.Type.values().firstOrNull {
                it.name == op.uppercase()
            }?.let { type ->
                Operation(type, a = varA.expression, b = variable(b)?.expression ?: Literal(b.toLong()))
            }?.simplify()
            varA.expression = operation?.value?.let { Literal(it) } ?: operation ?: run {
                val index = modelIndex++
                Input(
                    name = "$a$index",
                    // value = modelNumber[index].digitToInt().toLong(),
                )
            }
        }
        return z.value == 0L
    }

    private fun variable(variable: String): Variable? = when (variable) {
        "x" -> x
        "y" -> y
        "z" -> z
        "w" -> w
        else -> null
    }

    sealed interface Expression {
        val value: Long?
        val estimates: LongRange
    }

    data class Literal(override val value: Long) : Expression {
        override val estimates: LongRange
            get() = value..value

        override fun toString() = value.toString()

        companion object {
            val ZERO = Literal(0)
        }
    }

    data class Input(
        private val name: String,
        override var value: Long? = null,
    ) : Expression {
        override val estimates: LongRange
            get() = 1L..9L

        override fun toString() = value?.toString() ?: name
    }

    data class Variable(
        private val name: String,
        var expression: Expression = Literal.ZERO,
    ) : Expression {
        override val value: Long?
            get() = expression.value

        override val estimates: LongRange
            get() = expression.estimates

        override fun toString() = expression.toString()
    }

    data class Operation(
        val type: Type,
        val a: Expression,
        val b: Expression,
    ) : Expression {

        fun simplify(): Expression = when {
            type == Type.ADD && a.value == 0L -> b
            type == Type.ADD && b.value == 0L -> a
            type == Type.MUL && (a.value == 0L || b.value == 0L) -> Literal.ZERO
            type == Type.MUL && a.value == 1L -> b
            type == Type.MUL && b.value == 1L -> a
            type == Type.DIV && b.value == 1L -> a
            type == Type.EQL && a is Input && b.estimates.first > 9 -> Literal.ZERO
            type == Type.EQL && b is Input && a.estimates.first > 9 -> Literal.ZERO
            else -> this
        }

        override val estimates: LongRange
            get() = when (type) {
                Type.ADD -> (a.estimates.first + b.estimates.first)..(a.estimates.last + b.estimates.last)
                Type.MUL -> (a.estimates.first * b.estimates.first)..(a.estimates.last * b.estimates.last)
                Type.DIV -> (a.estimates.first / b.estimates.last)..(a.estimates.last / b.estimates.first)
                Type.MOD -> (if (a.estimates.last >= b.estimates.last) 0 else a.estimates.first)
                    .rangeTo(min(a.estimates.last, b.estimates.last - 1))
                Type.EQL -> 0L..1L
            }

        override val value: Long?
            get() = listOfNotNull(a, b)
                .mapNotNull { it.value }
                .takeIf { it.size == 2 }
                ?.let { (a, b) -> type.operation(a, b) }

        override fun toString() = "($a $type $b)"

        enum class Type(
            private val sign: String,
            val operation: (Long, Long) -> Long?,
        ) {
            ADD("+", { a, b -> a + b }),
            MUL("*", { a, b -> a * b }),
            DIV("/", { a, b -> a / b }),
            MOD("%", { a, b -> a % b }),
            EQL("==", { a, b -> if (a == b) 1 else 0 });

            override fun toString() = sign
        }
    }
}
