package com.rastadrian.advent

import com.rastadrian.advent.Day2.Game
import com.rastadrian.advent.Day2.Set
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class Day2Test {

    @Test
    fun testGameDeserialization() {
        val source = "Game 78: 7 red, 7 green; 8 blue; 6 green, 7 red, 5 blue"
        val expected = Game(
            id = 78, sets = listOf(
                Set(listOf(Pair(7, "red"), Pair(7, "green"))),
                Set(listOf(Pair(8, "blue"))),
                Set(listOf(Pair(6, "green"), Pair(7, "red"), Pair(5, "blue"))),
            )
        )

        val result = Day2().deserializeGame(source)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("colorCombinationsForPossibility")
    fun testIsGamePossible(red: Int, blue: Int, green: Int, shouldBePossible: Boolean) {
        val game = Game(
            id = 78, sets = listOf(
                Set(listOf(Pair(7, "red"), Pair(7, "green"))),
                Set(listOf(Pair(8, "blue"))),
                Set(listOf(Pair(6, "green"), Pair(7, "red"), Pair(5, "blue"))),
            )
        )

        val result = Day2().isGamePossible(game, red, blue, green)
        assertEquals(shouldBePossible, result)
    }

    @Test
    fun testFindMinimumSet() {
        val game = Game(
            id = 78, sets = listOf(
                Set(listOf(Pair(7, "red"), Pair(7, "green"))),
                Set(listOf(Pair(8, "blue"))),
                Set(listOf(Pair(6, "green"), Pair(7, "red"), Pair(5, "blue"))),
            )
        )

        // the largest number for any given color
        val expectedSet =
            Set(listOf(Pair(7, "red"), Pair(7, "green"), Pair(8, "blue")))

        val result = Day2().findMinimumSet(game)
        assertEquals(expectedSet, result)
    }

    companion object {
        @JvmStatic
        fun colorCombinationsForPossibility(): Stream<Arguments> =
            Stream.of(
                Arguments.of(5, 5, 5, false),
                Arguments.of(15, 15, 15, true)
            )
    }
}