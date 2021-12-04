package aoc2021.day18

import aoc2021.PuzzleTest
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class SnailfishTest : PuzzleTest<Sequence<SnailNumber>, Long>(
    puzzle = Snailfish,
    path = "examples/day-18.txt",
    part1 = 4140,
    part2 = 3993,
) {
    @ParameterizedTest
    @CsvSource(
        delimiter = ';',
        value = [
            "                                                [1,9];   21",
            "                                                [9,1];   29",
            "                                        [[9,1],[1,9]];  129",
            "                                    [[1,2],[[3,4],5]];  143",
            "                        [[[[1,1],[2,2]],[3,3]],[4,4]];  445",
            "                        [[[[3,0],[5,3]],[4,4]],[5,5]];  791",
            "                        [[[[5,0],[7,4]],[5,5]],[6,6]]; 1137",
            "                    [[[[0,7],4],[[7,8],[6,0]]],[8,1]]; 1384",
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]; 3488",
        ]
    )
    fun magnitude(input: String, expected: Long) {
        val number = Snailfish.parseInput(input)

        val actual = number.magnitude

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        delimiter = ';',
        value = [
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]; [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]; [[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]",
            "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]; [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]; [[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]",
            "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]; [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]; [[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]",
            "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]; [7,[5,[[3,8],[1,4]]]]; [[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]",
            "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]; [[2,[2,2]],[8,[8,1]]]; [[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]",
            "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]; [2,9]; [[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]",
            "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]; [1,[[[9,3],9],[[9,0],[0,7]]]]; [[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]",
            "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]; [[[5,[7,4]],7],1]; [[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]",
            "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]; [[[[4,2],2],6],[8,7]]; [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]",
        ]
    )
    fun sum(a: String, b: String, expected: String) {
        val sum = Snailfish.parseInput(a) + Snailfish.parseInput(b)

        assertThat(sum.toString()).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        delimiter = ';',
        value = [
            "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]; [[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        ]
    )
    fun reduce(input: String, expected: String) {
        val number = Snailfish.parseInput(input)

        number.reduce()

        assertThat(number.toString()).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        delimiter = ';',
        value = [
            "                [[[[[9,8],1],2],3],4];                 [[[[0,9],2],3],4]",
            "                [7,[6,[5,[4,[3,2]]]]];                 [7,[6,[5,[7,0]]]]",
            "                [[6,[5,[4,[3,2]]]],1];                 [[6,[5,[7,0]]],3]",
            "    [[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]];     [[3,[2,[8,0]]],[9,[5,[7,0]]]]",
            "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]; [[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]",
        ]
    )
    fun explode(input: String, expected: String) {
        val number = Snailfish.parseInput(input)

        number.explode()

        assertThat(number.toString()).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(strings = ["[1,2]", "[1,[2,3]]", "[[1,2],[3,4]]"])
    fun toString(input: String) {
        val number = Snailfish.parseInput(input)

        val actual = number.toString()

        assertThat(actual).isEqualTo(input)
    }
}
