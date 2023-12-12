package com.rastadrian.advent

import java.io.File

class Day1 {
    private val numbers = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
    private val numbersAsWords = arrayOf(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "zero"
    )

    fun extractDigits(line: String): Int {
        var digits = ""
        var lastDigit = ""
        for (char in line) {
            if (numbers.contains(char.toString())) {
                if (digits == "") {
                    digits += char
                }
                lastDigit = char.toString()
            }
        }
        digits += lastDigit
        return digits.toInt()
    }

    fun extractDigitsAndWords(line: String): Int {
        var digits = ""
        var lastDigit = ""
        var lineIndex = 0
        while (lineIndex < line.length) {
            val char = line[lineIndex]
            if (numbers.contains(char.toString())) {
                if (digits == "") {
                    digits += char
                }
                lastDigit = char.toString()
            } else {
                for ((index, numberAsWord) in numbersAsWords.withIndex()) { // one, two, three
                    val expectedLength = lineIndex + numberAsWord.length
                    if (line.length >= expectedLength) {
                        val subset = line.substring(lineIndex, lineIndex + numberAsWord.length)
                        if (numberAsWord == subset) {
                            val wordAsNumber: String = (index + 1).toString() // 1, 2 ,3
                            if (digits == "") {
                                digits += wordAsNumber
                            }
                            lastDigit = wordAsNumber
                            break
                        }
                    }
                }
            }
            lineIndex++
        }
        digits += lastDigit
        return digits.toInt()
    }

}

fun main() {
    val path = "/Users/adrian/workspace/advent-of-code/src/main/resources/Day1"
    val lines: List<String> = File(path).readLines()
    val day1 = Day1()

    // part one
    val sum1 = lines.sumOf {
        day1.extractDigits(it)
    }
    println(sum1)

    // part two
    val sum2 = lines.sumOf {
        day1.extractDigitsAndWords(it)
    }
    println(sum2)
}