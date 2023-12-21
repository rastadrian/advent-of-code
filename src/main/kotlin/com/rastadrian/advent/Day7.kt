package com.rastadrian.advent

import java.io.File
import java.lang.Integer.signum

val cards = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')

data class TypedHand(val handType: HandType, val content: String, val bid: Int)

data class Hand(val content: String, val bid: Int)

enum class HandType(val order: Int) {
    FIVE_OF_A_KIND(7) {
        override fun isHandOfType(play: String): Boolean {
            val sortedString = play.toList().sorted()
            return sortedString.first() == sortedString.last()
        }
    },
    FOUR_OF_A_KIND(6) {
        override fun isHandOfType(play: String): Boolean {
            val counters = play.countOccurrences()
            return counters.filter { it.value == 4 }.count() == 1
        }
    },
    FULL_HOUSE(5) {
        override fun isHandOfType(play: String): Boolean {
            val counters = play.countOccurrences()
            return counters.filter { it.value == 3 }.count() == 1 &&
                    counters.filter { it.value == 2 }.count() == 1
        }
    },
    THREE_OF_A_KIND(4) {
        override fun isHandOfType(play: String): Boolean {
            val counters = play.countOccurrences()
            return counters.filter { it.value == 3 }.count() == 1
                    && counters.filter { it.value == 1 }.count() == 2
        }
    },
    TWO_PAIR(3) {
        override fun isHandOfType(play: String): Boolean {
            val counters = play.countOccurrences()
            return counters.filter { it.value == 2 }.count() == 2
        }
    },
    ONE_PAIR(2) {
        override fun isHandOfType(play: String): Boolean {
            val counters = play.countOccurrences()
            return counters.filter { it.value == 2 }.count() == 1
                    && counters.filter { it.value == 1 }.count() == 3
        }
    },
    HIGH_CARD(1) {
        override fun isHandOfType(play: String): Boolean {
            val counters = play.countOccurrences()
            return counters.filter { it.value == 1 }.count() == 5
        }
    };

    abstract fun isHandOfType(play: String): Boolean

    fun String.countOccurrences(): Map<Char, Int> {
        val counters = HashMap<Char, Int>()
        forEach {
            if (counters.contains(it)) {
                counters[it] = counters[it]!! + 1
            } else {
                counters[it] = 1
            }
        }
        return counters
    }
}

class Day7 {
    fun deserializeHands(line: List<String>): List<Hand> =
        line.map {
            val split = it.split(" ")
            Hand(split[0], split[1].toInt())
        }

    fun sortHandsByType(hands: List<Hand>): List<TypedHand> {
        val handTypes = HandType.entries.toTypedArray()
        handTypes.sortByDescending { it.order }
        return hands
            .map { hand ->
                val type = handTypes.first { type -> type.isHandOfType(hand.content) }
                TypedHand(type, hand.content, hand.bid)
            }
            .sortedWith { hand1, hand2 -> signum(hand1.handType.order - hand2.handType.order) }
    }

    fun sortHandsByContent(hands: List<TypedHand>): List<TypedHand> {
        val handTypes = HandType.entries.toTypedArray()
        handTypes.sortBy { it.order }
        val fullySortedHands = ArrayList<TypedHand>()
        handTypes.forEach { type ->
            val sortedHandsForType = hands
                .filter { hand -> hand.handType == type }
                .sortedWith { hand1, hand2 ->
                    var comparison = 0
                    for (i in 0..4) {
                        if (comparison != 0) {
                            break
                        }
                        val firstHandCard = cards.indexOf(hand1.content[i])
                        val secondHandCard = cards.indexOf(hand2.content[i])
                        comparison = if (firstHandCard > secondHandCard) {
                            1
                        } else if (firstHandCard < secondHandCard) {
                            -1
                        } else {
                            0
                        }
                    }
                    signum(comparison)
                }
            fullySortedHands.addAll(sortedHandsForType)
        }
        return fullySortedHands
    }
}

fun main() {
    val lines = File("/Users/adrian/workspace/advent-of-code/src/main/resources/Day7")
        .bufferedReader().readLines()
    val day7 = Day7()

    // part one
    val hands = day7.deserializeHands(lines)
    val sortedHands = day7.sortHandsByType(hands)
    val fullySortedHands = day7.sortHandsByContent(sortedHands)
    val result = fullySortedHands.mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()
    println(result)
}