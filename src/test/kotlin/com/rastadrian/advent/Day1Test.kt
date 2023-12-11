package com.rastadrian.advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day1Test {

    @ParameterizedTest
    @MethodSource("inputs")
    fun extractDigits(input: String, expected: Int) {
        val result = Day1().extractDigits(input)
        assertEquals(expected, result)
    }

    @Test
    fun extractAllDigits() {
        val test = arrayOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet",
        )

        val result = test.sumOf {
            Day1().extractDigits(it)
        }

        assertEquals(142, result)
    }

    companion object {
        @JvmStatic
        fun inputs(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("a1b2c3d4e5f", 15),
                Arguments.of("treb7uchet", 77),
                Arguments.of("trebsevenuchet", 77),
                Arguments.of("pqr3stu8vwx", 38),
                Arguments.of("pqr3stueightvwx", 38),
                Arguments.of("1abc2", 12),
                Arguments.of("1abc2two", 12),
            )
        }
    }
}