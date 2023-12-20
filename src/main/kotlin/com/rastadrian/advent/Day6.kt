package com.rastadrian.advent

import java.io.File

data class Race(
    val time: Int,
    val distance: Int
)

class Day6 {

    fun deserializeRaces(lines: List<String>): List<Race> {
        val times = lines.first().substringAfter("Time:").split(" ").filter { it.isNotBlank() }
        val distances = lines.last().substringAfter("Distance:").split(" ").filter { it.isNotBlank() }
        return times.mapIndexed { index, time -> Race(time.toInt(), distances[index].toInt()) }
    }

    fun numberOfWaysToBeatRecord(race: Race): Int {
        var recordBeatCounter = 0
        for (i in 1..race.time) {
            if ((race.time - i) * i > race.distance) {
                recordBeatCounter++
            }
        }
        return recordBeatCounter
    }
}

fun main() {
    val lines = File("/Users/adrian/workspace/advent-of-code/src/main/resources/Day6")
        .bufferedReader().readLines()
    val day6 = Day6()
    val races = day6.deserializeRaces(lines)

    // part one
    val result = races.map { day6.numberOfWaysToBeatRecord(it) }.reduce { race1, race2 -> race1 * race2 }
    println(result)
}