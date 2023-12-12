package com.rastadrian.advent

import java.io.File

class Day2 {
    data class Set(
        val draws: List<Pair<Int, String>>
    )

    data class Game(val id: Int, val sets: List<Set>)

    fun isGamePossible(game: Game, red: Int, blue: Int, green: Int): Boolean {
        for (set in game.sets) {
            for (draw in set.draws) {
                val color = draw.second
                val cubeCount = draw.first
                if (color == "red" && cubeCount > red
                    || color == "blue" && cubeCount > blue
                    || color == "green" && cubeCount > green
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun findMinimumSet(game: Game): Set {
        var largestBlue = 0
        var largestGreen = 0
        var largestRed = 0
        for (set in game.sets) {
            for (draw in set.draws) {
                when (draw.second) {
                    "red" -> largestRed = maxOf(largestRed, draw.first)
                    "blue" -> largestBlue = maxOf(largestBlue, draw.first)
                    "green" -> largestGreen = maxOf(largestGreen, draw.first)
                }
            }
        }
        return Set(listOf(Pair(largestRed, "red"), Pair(largestGreen, "green"), Pair(largestBlue, "blue")))
    }

    fun deserializeGame(line: String): Game {
        val split = line.trim().split(":")
        val gameHeader = split[0]
        val gameId = gameHeader.split(" ")[1].toInt()
        val serializedSets = split[1].trim().split(";")
        val sets = serializedSets.map { set ->
            val draws = set.trim().split(",").map { draw ->
                val serializedDraw = draw.trim().split(" ")
                Pair(serializedDraw[0].toInt(), serializedDraw[1].trim())
            }
            Set(draws)
        }
        return Game(gameId, sets)
    }
}

fun main() {
    val path = "/Users/adrian/workspace/advent-of-code/src/main/resources/Day2"
    val lines: List<String> = File(path).readLines()
    val day2 = Day2()

    // part one
    val result =
        lines
            .map { day2.deserializeGame(it) }
            .filter { day2.isGamePossible(it, red = 12, blue = 14, green = 13) }
            .sumOf { it.id }
    println(result)

    // part two
    val result2 = lines
        .map {
            val game = day2.deserializeGame(it)
            day2.findMinimumSet(game)
        }.sumOf { set ->
            set.draws.map { it.first }.reduce { first, second -> first * second }
        }

    println(result2)
}