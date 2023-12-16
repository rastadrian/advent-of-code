package com.rastadrian.advent

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class Day4Test {

    @Test
    fun testParsing() {
        val example = "Card  50: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val expectedGame = Game(
            50,
            winningNumbers = listOf(41, 48, 83, 86, 17),
            cardNumbers = listOf(83, 86, 6, 31, 17, 9, 48, 53)
        )
        val result = Day4().parseLine(example)
        assertEquals(expectedGame, result)
    }

    @ParameterizedTest
    @MethodSource("matchers")
    fun testMatcher(line: String, expectedMatch: Int) {
        val day4 = Day4()
        val game = day4.parseLine(line)
        val result = day4.getMatches(game)
        assertEquals(expectedMatch, result)
    }

    companion object {
        @JvmStatic
        fun matchers(): Stream<Arguments> = Stream.of(
            Arguments.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", 8),
            Arguments.of("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", 2),
            Arguments.of("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", 2),
            Arguments.of("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", 1),
            Arguments.of("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", 0),
            Arguments.of("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", 0),
        )

    }
}