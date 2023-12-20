package com.rastadrian.advent

import java.io.File

data class Race(
    val time: Long,
    val distance: Long
)

class Day6 {

    fun deserializeRaces(lines: List<String>): List<Race> {
        val times = lines.first().substringAfter("Time:").split(" ").filter { it.isNotBlank() }
        val distances = lines.last().substringAfter("Distance:").split(" ").filter { it.isNotBlank() }
        return times.mapIndexed { index, time -> Race(time.toLong(), distances[index].toLong()) }
    }

    fun deserializeRaces2(lines: List<String>): Race {
        val times = lines.first().substringAfter("Time:")
            .split(" ").filter { it.isNotBlank() }
            .reduce { time1, time2 -> time1 + time2 }
        val distances = lines.last().substringAfter("Distance:")
            .split(" ").filter { it.isNotBlank() }
            .reduce { dist1, dist2 -> dist1 + dist2 }
        return Race(times.toLong(), distances.toLong())
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

    // part one
    val races = day6.deserializeRaces(lines)
    val result1 = races.map { day6.numberOfWaysToBeatRecord(it) }.reduce { race1, race2 -> race1 * race2 }
    println(result1)

    // part two
    val race = day6.deserializeRaces2(lines)
    val result2 = day6.numberOfWaysToBeatRecord(race)
    println(result2)
}