
import extensions.filePathToStringList
import kotlin.math.pow


fun List<String>.toCamelCards(): List<Pair<String, Int>> = this.map {
    val (hand, bid) = it.split(" ")
    return@map hand to bid.toInt()
}

fun Char.cardValue(deck: String): Int = deck
    .withIndex()
    .first { (_, card) -> this == card }
    .let { (index, _) -> index }

fun String.handValue(deck: String): Int = this
    .reversed()
    .mapIndexed{ index, card ->  card.cardValue(deck) * 10.0.pow(2*index) }
    .sum()
    .toInt()

fun String.rankValue(): Int = this
    .groupBy{ it }
    .mapValues { it.value.count() }
    .map { 10.0.pow(it.value-1).toInt() }
    .sum()

fun String.bestHand(deck: String): String {
    if ('J' !in this)
        return this

    val possibleHands = (this.replace("J", "") + deck.last()).toSet().map { sub -> this.replace("J", sub.toString()) }
    val ranking = possibleHands
        .map { hand -> Rank(
            bestHandRank = hand.rankValue(),
            bestHand = hand.handValue(deck),
            handRank = this.rankValue(),
            hand = this.handValue(deck),
            camelCards = hand
        )}
        .sorted()

    return ranking.last().camelCards
}

data class Rank(
    val bestHandRank: Int = 0,
    val bestHand: Int = 0,
    val handRank: Int = 0,
    val hand: Int = 0,
    val bid: Int = 0,
    val camelCards: String = ""
) : Comparable<Rank> {
    override fun compareTo(other: Rank) = compareValuesBy(this, other,
        { it.bestHandRank },
        { it.bestHand },
        { it.handRank },
        { it.hand },
    )
}

fun process(lines: List<Pair<String, Int>>, deck: String): Int {
    val result = lines
        .map { (hand, bid) ->
            val bestHand = if (deck[0] == 'J') hand.bestHand(deck) else hand
            return@map Rank(
                bestHandRank = bestHand.rankValue(),
                bestHand = bestHand.handValue(deck),
                handRank = hand.rankValue(),
                hand = hand.handValue(deck),
                bid = bid,
                camelCards = "$bestHand -> $hand"
            )
        }
        .sorted()
        .onEach { println(it) }
        .mapIndexed { index, ranking -> (index + 1) * (ranking.bid) }
        .sum()

    return 0
}

fun process1(lines: List<Pair<String, Int>>, deck: String): Int = 0

fun main() {
    val camelCards = "Day07Input.txt".filePathToStringList().toCamelCards()
    println("task1 ${process(camelCards, "23456789TJQKA")}")
    println("task2 ${process(camelCards, "J23456789TQKA")}")
}
