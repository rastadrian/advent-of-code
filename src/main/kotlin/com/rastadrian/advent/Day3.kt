package com.rastadrian.advent

import java.io.File

class Day3(private val matrix: Array<Array<Char>>) {

    private val numbers = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')
    private val irrelevantSymbols = arrayOf('.', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0')

    fun sumOfAllPartNumbers(): Int {
        val partNumbers = ArrayList<Int>()
        for ((columnIndex, row) in matrix.withIndex()) {
            var currentNumber = ""
            var isCurrentNumberValid = false
            for ((characterIndex, character) in row.withIndex()) {
                if (numbers.contains(character)) {
                    currentNumber += character
                    isCurrentNumberValid =
                        isCurrentNumberValid || foundSpecialCharactersAround(columnIndex, characterIndex)
                    if (characterIndex == row.size - 1 && isCurrentNumberValid) { //end
                        partNumbers.add(currentNumber.toInt())
                        isCurrentNumberValid = false
                    }
                } else if (currentNumber != "" && isCurrentNumberValid) {
                    partNumbers.add(currentNumber.toInt())
                    isCurrentNumberValid = false
                    currentNumber = ""
                } else {
                    currentNumber = ""
                    isCurrentNumberValid = false
                }
            }
        }
        return partNumbers.sum()
    }

    fun sumOfAllGearRatios(): Int {
        val asterisks = HashMap<String, ArrayList<Int>>()
        for ((rowIndex, _) in matrix.withIndex()) {
            for ((charIndex, char) in matrix[rowIndex].withIndex()) {
                if (char == '*') {
                    asterisks["$rowIndex,$charIndex"] = ArrayList()
                }
            }
        }
        for ((columnIndex, row) in matrix.withIndex()) {
            var currentNumber = ""
            val currentAsterisks = HashSet<String>()
            for ((characterIndex, character) in row.withIndex()) {
                if (numbers.contains(character)) {
                    currentNumber += character
                    currentAsterisks += asterisksAround(columnIndex, characterIndex)
                    if (characterIndex == row.size - 1 && currentAsterisks.isNotEmpty()) { //end
                        currentAsterisks.forEach {
                            asterisks[it]?.add(currentNumber.toInt())
                        }
                        currentAsterisks.clear()
                    }
                } else if (currentNumber != "" && currentAsterisks.isNotEmpty()) {
                    currentAsterisks.distinct().forEach {
                        asterisks[it]?.add(currentNumber.toInt())
                    }
                    currentAsterisks.clear()
                    currentNumber = ""
                } else {
                    currentNumber = ""
                    currentAsterisks.clear()
                }
            }
        }
        return asterisks.filter { it.value.size == 2 }.map { it.value.reduce { first, second -> first * second } }.sum()
    }

    private fun asterisksAround(columnIndex: Int, rowIndex: Int): HashSet<String> {
        val asteriskCoordinates = HashSet<String>()
        for (i in -1..1) {
            for (j in -1..1) {
                if (isAsterisk(columnIndex + i, rowIndex + j)) {
                    asteriskCoordinates += "${columnIndex + i},${rowIndex + j}"
                }
            }
        }
        return asteriskCoordinates
    }

    private fun foundSpecialCharactersAround(columnIndex: Int, rowIndex: Int): Boolean {
        var foundSpecialCharacter = false
        for (i in -1..1) {
            for (j in -1..1) {
                foundSpecialCharacter = foundSpecialCharacter || isSpecialCharacter(columnIndex + i, rowIndex + j)
            }
        }
        return foundSpecialCharacter
    }

    private fun isAsterisk(columnIndex: Int, rowIndex: Int): Boolean =
        columnIndex >= 0 && columnIndex < matrix.size
                && rowIndex >= 0 && rowIndex < matrix[columnIndex].size
                && matrix[columnIndex][rowIndex] == '*'

    private fun isSpecialCharacter(columnIndex: Int, rowIndex: Int): Boolean =
        columnIndex >= 0 && columnIndex < matrix.size
                && rowIndex >= 0 && rowIndex < matrix[columnIndex].size
                && !irrelevantSymbols.contains(matrix[columnIndex][rowIndex])

    companion object {
        fun fromSchematic(lines: List<String>): Day3 {
            val matrix = lines.map { Array(it.length) { index -> it[index] } }.toTypedArray()
            return Day3(matrix)
        }
    }
}

fun main() {
    val path = "/Users/adrian/workspace/advent-of-code/src/main/resources/Day3"
    val lines: List<String> = File(path).readLines()
    val day3 = Day3.fromSchematic(lines)

    // part one
    val result = day3.sumOfAllPartNumbers()
    println(result)

    // part two
    val result2 = day3.sumOfAllGearRatios()
    println(result2)
}