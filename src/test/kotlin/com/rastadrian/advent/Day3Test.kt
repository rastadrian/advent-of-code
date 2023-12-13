package com.rastadrian.advent

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class Day3Test {

    @ParameterizedTest
    @MethodSource("schematics")
    fun testMatrix(schematic: List<String>, expectedSum: Int) {
        val result = Day3.fromSchematic(schematic).sumOfAllPartNumbers()
        assertEquals(expectedSum, result)
    }

    @ParameterizedTest
    @MethodSource("gearRatios")
    fun testGearRatios(schematic: List<String>, expectedSum: Int) {
        val result = Day3.fromSchematic(schematic).sumOfAllGearRatios()
        assertEquals(expectedSum, result)
    }

    companion object {

        @JvmStatic
        fun gearRatios(): Stream<Arguments> = Stream.of(
            Arguments.of(
                listOf(
                    "467..114..",
                    "...*......",
                    "..35..633.",
                    "......#...",
                    "617*......",
                    ".....+.58.",
                    "..592.....",
                    "......755.",
                    "...$.*....",
                    ".664.598..",
                ),
                467835
            ),
        )

        @JvmStatic
        fun schematics(): Stream<Arguments> = Stream.of(
            Arguments.of(
                listOf(
                    "467..114..",
                    "...*......",
                    "..35..633.",
                    "......#...",
                    "617*......",
                    ".....+.58.",
                    "..592.....",
                    "......755.",
                    "...$.*....",
                    ".664.598..",
                ),
                4361
            ),
            Arguments.of(
                listOf(
                    "....66",
                    ".4....",
                    "..%..."
                ),
                4
            ),
            Arguments.of(
                listOf(
                    "..552........968",
                    "............*...",
                    "......639....467"
                ),
                1435
            ),
            Arguments.of(
                listOf(
                    "...259.........",
                    "..$.....%..431+",
                    "3....504.......",
                    ".........684...",
                    "....415...+....",
                ),
                1878
            ),
            Arguments.of(
                listOf(
                    "..351..........937.",
                    ".......492.247.....",
                    ".........*..*...933",
                    "....561.271.349....",
                ),
                1359
            ),
            Arguments.of(
                listOf(
                    "...215........-450..........",
                    "....=....88...........=415..",
                    "856.....%....765............",
                ),
                1168
            ),
        )
    }
}