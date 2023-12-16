package com.rastadrian.advent

import java.io.File

data class Game(
    val id: Int,
    val winningNumbers: List<Int>,
    val cardNumbers: List<Int>
)

class Day4 {

    fun parseLine(line: String): Game {
        val initialSplit = line.split("|")
        val id = initialSplit[0].split(":")[0].split(" ").last().trim().toInt()
        val winningNumbers =
            initialSplit[0].split(":")[1].split(" ")
                .filter { it.isNotEmpty() }
                .map { it.trim().toInt() }
        val cardNumbers = initialSplit[1].split(" ")
            .filter { it.isNotEmpty() }
            .map { it.trim().toInt() }
        return Game(id, winningNumbers, cardNumbers)
    }

    fun getMatches(game: Game): Int {
        var matches = 0
        for (cardNumber in game.cardNumbers) {
            if (game.winningNumbers.contains(cardNumber)) {
                matches = if (matches == 0) 1 else matches * 2
            }
        }
        return matches
    }

    fun howManyScratches(games: List<Game>) : Int {
        val cardCounters = Array(games.size) { _ -> 1}
        for (game in games) {
            val currentGameIndex = game.id - 1
            val matches = getPart2Matches(game)
            if (matches == 0) {
                continue
            }
            for (i in game.id..<game.id+matches) {
                if (i < games.size) {
                    cardCounters[i] += cardCounters[currentGameIndex]
                }
            }
        }
        return cardCounters.sum()
    }

    private fun getPart2Matches(game: Game) : Int {
        var matches = 0
        for (cardNumber in game.cardNumbers) {
            if (game.winningNumbers.contains(cardNumber)) {
                matches++
            }
        }
        return matches
    }
}

fun main() {
    val path = "/Users/adrian/workspace/advent-of-code/src/main/resources/Day4"
    val lines: List<String> = File(path).readLines()
    val day4 = Day4()
    val games = lines.map { day4.parseLine(it) }

    // part one
    val result = games.sumOf { day4.getMatches(it) }
    println(result)

    // part two
    val result2 = day4.howManyScratches(games)
    println(result2)
}