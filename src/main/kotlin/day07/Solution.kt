package day07

import extensions.filePathToStringList
import kotlin.math.pow


fun List<String>.toCamelCards(): List<Pair<String, Int>> = this.map {
    val (hand, bid) = it.split(" ")
    return@map hand to bid.toInt()
}

fun Char.cardValue(deck: String): Int =
    deck.withIndex().first { (_, card) -> this == card }.index

fun String.handValue(deck: String): Int =
    this.reversed().mapIndexed { index, card -> (card.cardValue(deck) * 10.0.pow(2 * index)).toInt() }.sum()

fun String.rankValue(): Int =
    this.groupBy { it }.mapValues { it.value.count() }.map { 10.0.pow(it.value - 1).toInt() }.sum()

fun String.bestHand(deck: String): String = deck
    .map { card -> this.replace("J", card.toString()) }
    .map { hand -> Ranking(hand.rankValue(), this.handValue(deck), hand) }
    .maxOf { it }
    .camelCards

data class Ranking(val rank: Int = 0, val hand: Int = 0, val camelCards: String = "", val bid: Int = 0) :
    Comparable<Ranking> {
    override fun compareTo(other: Ranking) = compareValuesBy(this, other, { it.rank }, { it.hand })
}

fun process(lines: List<Pair<String, Int>>, deck: String): Int = lines
    .map { (hand, bid) ->
        val bestHand = if (deck[0] == 'J') hand.bestHand(deck) else hand
        return@map Ranking(bestHand.rankValue(), hand.handValue(deck), "$bestHand -> $hand", bid)
    }
    .sorted()
    .mapIndexed { index, ranking -> (index + 1) * (ranking.bid) }
    .sum()

fun main() {
    val camelCards = "Day07Input.txt".filePathToStringList().toCamelCards()
    println("task1 ${process(camelCards, "23456789TJQKA")}")
    println("task2 ${process(camelCards, "J23456789TQKA")}")
}
