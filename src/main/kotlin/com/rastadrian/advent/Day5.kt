package com.rastadrian.advent

import java.io.File

data class MappingEntry(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long)
data class Mapping(val source: String, val destination: String, val entries: List<MappingEntry>)
data class Almanac(val seeds: List<Long>, val mappings: List<Mapping>)

class Day5 {

    fun deserializeAlmanac(lines: List<String>): Almanac {
        val seeds = lines.first().substringAfter("seeds: ").split(" ").map { it.toLong() }
        val mappings = ArrayList<Mapping>()
        val currentEntries = ArrayList<MappingEntry>()
        var currentMappingSource = ""
        var currentMappingDestination = ""
        for ((index, line) in lines.withIndex()) {
            if (index == 0 || line.isBlank()) continue
            if (line.contains(" map:")) {
                if (currentMappingSource.isNotBlank()) {
                    mappings.add(Mapping(currentMappingSource, currentMappingDestination, ArrayList(currentEntries)))
                }
                val mappingText = line.split(" ")[0]
                currentMappingSource = mappingText.split("-")[0]
                currentMappingDestination = mappingText.split("-")[2]
                currentEntries.clear()
            } else {
                val mappingText = line.split(" ")
                currentEntries.add(
                    MappingEntry(mappingText[0].toLong(), mappingText[1].toLong(), mappingText[2].toLong())
                )
            }
            if (index == lines.size - 1) {
                mappings.add(Mapping(currentMappingSource, currentMappingDestination, ArrayList(currentEntries)))
            }
        }
        return Almanac(seeds, mappings)
    }
}

fun Almanac.findMappingForCategory(
    sourceCategory: String,
    sourceValues: List<Long>,
    destinationCategory: String,
): List<Long> {
    val mapping = mappings.firstOrNull { it.source == sourceCategory }
    if (mapping == null) {
        return sourceValues
    }
    val mappedValues = sourceValues.map {
        findMappingForSingleValue(it, mapping)
    }
    return findMappingForCategory(mapping.destination, mappedValues, destinationCategory)
}

fun Almanac.findMappingForCategoryRange(
    sourceCategory: String,
    sourceRanges: List<LongRange>,
    destinationCategory: String,
): List<LongRange> {
    val mapping = mappings.firstOrNull { it.source == sourceCategory }

    if (mapping == null) {
        return sourceRanges
    }

    val newRanges = sourceRanges.flatMap { currentRange ->

        // [50, 51 ,52 ,53, 54, 55, 56] <- currentRange
        //52, 9 = [52, 53, 54, 55] <- mapping
        //86, 5 = [86, 87, 88, 89, 90, 91]

        val intersectedMappings =
            mapping.entries.filter { it.sourceStart >= currentRange.first && it.sourceStart <= currentRange.last }

        if (intersectedMappings.isEmpty()) {
            return@flatMap listOf(currentRange)
        }

        val newRanges = intersectedMappings.map {
            val mappingSourceLast = it.sourceStart + it.rangeLength
            val newRangeLength = if (currentRange.last <= mappingSourceLast) {
                currentRange.last - it.sourceStart
            } else {
                mappingSourceLast - it.sourceStart
            }
            LongRange(it.destinationStart, it.destinationStart + newRangeLength)
        }

        var startRange: LongRange? = null
        val smallestNumber = newRanges.minOf { it.first }
        if (smallestNumber > currentRange.first) {
            startRange = LongRange(currentRange.first, smallestNumber - 1)
        }

        var endRange: LongRange? = null
        val largestNumber = newRanges.maxOf { it.last }
        if (largestNumber < currentRange.last) {
            endRange = LongRange(largestNumber + 1, currentRange.last)
        }

        (newRanges + startRange + endRange).filterNotNull()
    }
    return findMappingForCategoryRange(mapping.destination, newRanges, destinationCategory)
}

private fun findMappingForSingleValue(
    sourceValue: Long,
    mapping: Mapping,
): Long =
    mapping.entries
        .firstOrNull { sourceValue >= it.sourceStart && sourceValue <= it.sourceStart + it.rangeLength }
        ?.let { it.destinationStart + (sourceValue - it.sourceStart) } ?: sourceValue

fun main() {
    val lines = File("/Users/adrian/workspace/advent-of-code/src/main/resources/Day5")
        .bufferedReader().readLines()
    val day5 = Day5()

    val almanac = day5.deserializeAlmanac(lines)

    // part one

    val results = almanac.findMappingForCategory(
        sourceCategory = "seed",
        sourceValues = almanac.seeds,
        destinationCategory = "location"
    )
    println(results.min())

    // part two - not completed
//    val actualSeeds = almanac.seeds.withIndex()
//        .filter { (index, _) -> index % 2 != 0 }
//        .map { (index, rangeLength) -> LongRange(almanac.seeds[index - 1], almanac.seeds[index - 1] + rangeLength) }
//    val results2 = almanac.findMappingForCategoryRange(
//        sourceCategory = "seed",
//        sourceRanges = actualSeeds,
//        destinationCategory = "location"
//    )
//    println(results2.minOfOrNull { it.first })
}