package aoc2023.day07


import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("process1 of test input") {
        val input = "aoc2023/Day07TestInput1.txt".filePathToStringList().toCamelCards()
        forAll(
            row("23456789TJQKA", 6440),
            row("J23456789TQKA", 5905),
        ) { deck, expected ->
            When("deck = $deck") {
                Then("result should be $expected") {
                    process(input, deck) shouldBe expected
                }
            }
        }
    }

//    Given("process1 of prod input") {
//        val input = "Day07Input.txt".filePathToStringList().toCamelCards()
//        forAll(
//            row("23456789TJQKA", 248453531),
//            row("J23456789TQKA", 248781813),
//        ) { deck, expected ->
//            When("deck = $deck") {
//                Then("result should be $expected") {
//                    process(input, deck) shouldBe expected
//                }
//            }
//        }
//    }
})