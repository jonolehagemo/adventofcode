package day23

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("process1 of test input") {
        forAll(
            row("Day23TestInput1.txt", 94),
        ) { filepath, expected ->
            Then(filepath) {
                process1(getInput(filepath)) shouldBe expected
            }
        }
    }

    Given("ElvenMap.start") {
        forAll(
            row("Day23TestInput1.txt", Coordinate(0, 1), Coordinate(22, 21)),
        ) { filepath, expectedStart, expectedEnd ->
            When("getting test input") {
                val sut = getInput(filepath)
                Then("ElvenMap.start should be as expected") {
                    sut.start shouldBe expectedStart
                }
                Then("ElvenMap.end should be as expected") {
                    sut.end shouldBe expectedEnd
                }

            }
        }
    }

    Given("ElvenMap.neighbours") {
        forAll(
            row(Coordinate(0, 1), 'S', listOf(Coordinate(1, 1))),
            row(Coordinate(1, 1), '.', listOf(Coordinate(1, 2))),
            row(Coordinate(1, 2), '.', listOf(Coordinate(1, 3), Coordinate(1, 1))),
            row(Coordinate(4, 3), 'v', listOf(Coordinate(5, 3))),
            row(Coordinate(5, 3), '.', listOf(Coordinate(5, 4), Coordinate(6, 3))),
            row(Coordinate(22, 21), 'E', emptyList()),
        ) { coordinate, expectedTile, expectedList ->
            When(coordinate.toString()) {
                val map = getInput("Day23TestInput1.txt")
                val tile = map.tile(coordinate)
                val neighbours = map.neighbours(coordinate)

                Then("tile should be as expected") {
                    tile shouldBe expectedTile
                }
                Then("neighbours should be as expected") {
                    neighbours shouldBe expectedList
                }

            }
        }
    }

    Given("map to move on") {
        val elvenMap = getInput("Day23TestInput1.txt")
        println(elvenMap)

        When("move started") {
            val result = move(setOf(elvenMap.start), elvenMap.start.south(), elvenMap)
            println(elvenMap)
            Then("neighbours should be as expected") {
                result shouldBe 94
            }

        }
    }

    Given("map without slopes") {
        val elvenMap = getInput("Day23TestInput1.txt").removeSlopes()
        println("y.size = ${elvenMap.elvenMap.size}, x.size = ${elvenMap.elvenMap.first().length}")

        When("move started") {
            val result = move(setOf(elvenMap.start), elvenMap.start.south(), elvenMap)
            println("cache keys = ${elvenMap.neighboursMap.keys.size}")
            Then("neighbours should be as expected") {
                result shouldBe 154
            }

        }
    }
})

//neighboursMap={1|1=[Coordinate(y=1, x=2)], 1|2=[Coordinate(y=1, x=3), Coordinate(y=1, x=1)], 1|3=[Coordinate(y=1, x=4), Coordinate(y=1, x=2)], 1|4=[Coordinate(y=1, x=5), Coordinate(y=1, x=3)], 1|5=[Coordinate(y=1, x=6), Coordinate(y=1, x=4)], 1|6=[Coordinate(y=1, x=7), Coordinate(y=1, x=5)], 1|7=[Coordinate(y=2, x=7), Coordinate(y=1, x=6)], 2|7=[Coordinate(y=1, x=7), Coordinate(y=3, x=7)], 3|7=[Coordinate(y=2, x=7), Coordinate(y=3, x=6)], 3|6=[Coordinate(y=3, x=7), Coordinate(y=3, x=5)], 3|5=[Coordinate(y=3, x=6), Coordinate(y=3, x=4)], 3|4=[Coordinate(y=3, x=5), Coordinate(y=3, x=3)], 3|3=[Coordinate(y=3, x=4), Coordinate(y=4, x=3)], 4|3=[Coordinate(y=3, x=3), Coordinate(y=5, x=3)], 5|3=[Coordinate(y=4, x=3), Coordinate(y=5, x=4), Coordinate(y=6, x=3)], 5|4=[Coordinate(y=5, x=5), Coordinate(y=5, x=3)], 5|5=[Coordinate(y=5, x=6), Coordinate(y=5, x=4)], 5|6=[Coordinate(y=5, x=7), Coordinate(y=5, x=5)], 5|7=[Coordinate(y=6, x=7), Coordinate(y=5, x=6)], 6|7=[Coordinate(y=5, x=7), Coordinate(y=7, x=7)], 7|7=[Coordinate(y=6, x=7), Coordinate(y=8, x=7)], 8|7=[Coordinate(y=7, x=7), Coordinate(y=9, x=7)], 9|7=[Coordinate(y=8, x=7), Coordinate(y=10, x=7)], 10|7=[Coordinate(y=9, x=7), Coordinate(y=11, x=7)], 11|7=[Coordinate(y=10, x=7), Coordinate(y=11, x=8)], 11|8=[Coordinate(y=11, x=9), Coordinate(y=11, x=7)], 11|9=[Coordinate(y=10, x=9), Coordinate(y=11, x=8)], 10|9=[Coordinate(y=9, x=9), Coordinate(y=11, x=9)], 9|9=[Coordinate(y=8, x=9), Coordinate(y=10, x=9)], 8|9=[Coordinate(y=7, x=9), Coordinate(y=9, x=9)], 7|9=[Coordinate(y=6, x=9), Coordinate(y=8, x=9)], 6|9=[Coordinate(y=5, x=9), Coordinate(y=7, x=9)], 5|9=[Coordinate(y=4, x=9), Coordinate(y=6, x=9)], 4|9=[Coordinate(y=3, x=9), Coordinate(y=5, x=9)], 3|9=[Coordinate(y=3, x=10), Coordinate(y=4, x=9)], 3|10=[Coordinate(y=3, x=11), Coordinate(y=3, x=9)], 3|11=[Coordinate(y=3, x=12), Coordinate(y=4, x=11), Coordinate(y=3, x=10)], 3|12=[Coordinate(y=3, x=13), Coordinate(y=3, x=11)], 3|13=[Coordinate(y=4, x=13), Coordinate(y=3, x=12)], 4|13=[Coordinate(y=3, x=13), Coordinate(y=5, x=13)], 5|13=[Coordinate(y=4, x=13), Coordinate(y=5, x=14)], 5|14=[Coordinate(y=5, x=15), Coordinate(y=5, x=13)], 5|15=[Coordinate(y=5, x=16), Coordinate(y=5, x=14)], 5|16=[Coordinate(y=5, x=17), Coordinate(y=5, x=15)], 5|17=[Coordinate(y=4, x=17), Coordinate(y=5, x=16)], 4|17=[Coordinate(y=3, x=17), Coordinate(y=5, x=17)], 3|17=[Coordinate(y=2, x=17), Coordinate(y=4, x=17)], 2|17=[Coordinate(y=1, x=17), Coordinate(y=3, x=17)], 1|17=[Coordinate(y=1, x=18), Coordinate(y=2, x=17)], 1|18=[Coordinate(y=1, x=19), Coordinate(y=1, x=17)], 1|19=[Coordinate(y=2, x=19), Coordinate(y=1, x=18)], 2|19=[Coordinate(y=1, x=19), Coordinate(y=3, x=19)], 3|19=[Coordinate(y=2, x=19), Coordinate(y=4, x=19)], 4|19=[Coordinate(y=3, x=19), Coordinate(y=5, x=19)], 5|19=[Coordinate(y=4, x=19), Coordinate(y=5, x=20)], 5|20=[Coordinate(y=5, x=21), Coordinate(y=5, x=19)], 5|21=[Coordinate(y=6, x=21), Coordinate(y=5, x=20)], 6|21=[Coordinate(y=5, x=21), Coordinate(y=7, x=21)], 7|21=[Coordinate(y=6, x=21), Coordinate(y=7, x=20)], 7|20=[Coordinate(y=7, x=21), Coordinate(y=7, x=19)], 7|19=[Coordinate(y=7, x=20), Coordinate(y=8, x=19)], 8|19=[Coordinate(y=7, x=19), Coordinate(y=9, x=19)], 9|19=[Coordinate(y=8, x=19), Coordinate(y=9, x=20)], 9|20=[Coordinate(y=9, x=21), Coordinate(y=9, x=19)], 9|21=[Coordinate(y=10, x=21), Coordinate(y=9, x=20)], 10|21=[Coordinate(y=9, x=21), Coordinate(y=11, x=21)], 11|21=[Coordinate(y=10, x=21), Coordinate(y=12, x=21), Coordinate(y=11, x=20)], 12|21=[Coordinate(y=11, x=21), Coordinate(y=13, x=21)], 13|21=[Coordinate(y=12, x=21), Coordinate(y=14, x=21)], 14|21=[Coordinate(y=13, x=21), Coordinate(y=15, x=21)], 15|21=[Coordinate(y=14, x=21), Coordinate(y=15, x=20)], 15|20=[Coordinate(y=15, x=21), Coordinate(y=15, x=19)], 15|19=[Coordinate(y=15, x=20), Coordinate(y=16, x=19)], 16|19=[Coordinate(y=15, x=19), Coordinate(y=17, x=19)], 17|19=[Coordinate(y=16, x=19), Coordinate(y=18, x=19)], 18|19=[Coordinate(y=17, x=19), Coordinate(y=19, x=19)], 19|19=[Coordinate(y=18, x=19), Coordinate(y=20, x=19), Coordinate(y=19, x=18)], 20|19=[Coordinate(y=19, x=19), Coordinate(y=21, x=19)], 21|19=[Coordinate(y=20, x=19), Coordinate(y=21, x=20)], 21|20=[Coordinate(y=21, x=21), Coordinate(y=21, x=19)], 21|21=[Coordinate(y=22, x=21), Coordinate(y=21, x=20)], 19|18=[Coordinate(y=19, x=19), Coordinate(y=19, x=17)], 19|17=[Coordinate(y=19, x=18), Coordinate(y=20, x=17)], 20|17=[Coordinate(y=19, x=17), Coordinate(y=21, x=17)], 21|17=[Coordinate(y=20, x=17), Coordinate(y=21, x=16)], 21|16=[Coordinate(y=21, x=17), Coordinate(y=21, x=15)], 21|15=[Coordinate(y=20, x=15), Coordinate(y=21, x=16)], 20|15=[Coordinate(y=19, x=15), Coordinate(y=21, x=15)], 19|15=[Coordinate(y=20, x=15), Coordinate(y=19, x=14)], 19|14=[Coordinate(y=19, x=15), Coordinate(y=19, x=13)], 19|13=[Coordinate(y=18, x=13), Coordinate(y=19, x=14), Coordinate(y=19, x=12)], 18|13=[Coordinate(y=17, x=13), Coordinate(y=19, x=13)], 17|13=[Coordinate(y=18, x=13), Coordinate(y=17, x=12)], 17|12=[Coordinate(y=17, x=13), Coordinate(y=17, x=11)], 17|11=[Coordinate(y=16, x=11), Coordinate(y=17, x=12)], 16|11=[Coordinate(y=15, x=11), Coordinate(y=17, x=11)], 15|11=[Coordinate(y=15, x=12), Coordinate(y=16, x=11)], 15|12=[Coordinate(y=15, x=13), Coordinate(y=15, x=11)], 15|13=[Coordinate(y=14, x=13), Coordinate(y=15, x=12)], 14|13=[Coordinate(y=13, x=13), Coordinate(y=15, x=13)], 13|13=[Coordinate(y=12, x=13), Coordinate(y=13, x=14), Coordinate(y=14, x=13), Coordinate(y=13, x=12)], 12|13=[Coordinate(y=11, x=13), Coordinate(y=13, x=13)], 11|13=[Coordinate(y=12, x=13), Coordinate(y=11, x=12)], 11|12=[Coordinate(y=11, x=13), Coordinate(y=11, x=11)], 11|11=[Coordinate(y=10, x=11), Coordinate(y=11, x=12)], 10|11=[Coordinate(y=9, x=11), Coordinate(y=11, x=11)], 9|11=[Coordinate(y=9, x=12), Coordinate(y=10, x=11)], 9|12=[Coordinate(y=9, x=13), Coordinate(y=9, x=11)], 9|13=[Coordinate(y=9, x=14), Coordinate(y=9, x=12)], 9|14=[Coordinate(y=9, x=15), Coordinate(y=9, x=13)], 9|15=[Coordinate(y=9, x=16), Coordinate(y=9, x=14)], 9|16=[Coordinate(y=9, x=17), Coordinate(y=9, x=15)], 9|17=[Coordinate(y=8, x=17), Coordinate(y=9, x=16)], 8|17=[Coordinate(y=7, x=17), Coordinate(y=9, x=17)], 7|17=[Coordinate(y=8, x=17), Coordinate(y=7, x=16)], 7|16=[Coordinate(y=7, x=17), Coordinate(y=7, x=15)], 7|15=[Coordinate(y=7, x=16), Coordinate(y=7, x=14)], 7|14=[Coordinate(y=7, x=15), Coordinate(y=7, x=13)], 7|13=[Coordinate(y=7, x=14), Coordinate(y=7, x=12)], 7|12=[Coordinate(y=7, x=13), Coordinate(y=7, x=11)], 7|11=[Coordinate(y=6, x=11), Coordinate(y=7, x=12)], 6|11=[Coordinate(y=5, x=11), Coordinate(y=7, x=11)], 5|11=[Coordinate(y=4, x=11), Coordinate(y=6, x=11)], 4|11=[Coordinate(y=3, x=11), Coordinate(y=5, x=11)], 13|14=[Coordinate(y=13, x=15), Coordinate(y=13, x=13)], 13|15=[Coordinate(y=14, x=15), Coordinate(y=13, x=14)], 14|15=[Coordinate(y=13, x=15), Coordinate(y=15, x=15)], 15|15=[Coordinate(y=14, x=15), Coordinate(y=16, x=15)], 16|15=[Coordinate(y=15, x=15), Coordinate(y=17, x=15)], 17|15=[Coordinate(y=16, x=15), Coordinate(y=17, x=16)], 17|16=[Coordinate(y=17, x=17), Coordinate(y=17, x=15)], 17|17=[Coordinate(y=16, x=17), Coordinate(y=17, x=16)], 16|17=[Coordinate(y=15, x=17), Coordinate(y=17, x=17)], 15|17=[Coordinate(y=14, x=17), Coordinate(y=16, x=17)], 14|17=[Coordinate(y=13, x=17), Coordinate(y=15, x=17)], 13|17=[Coordinate(y=12, x=17), Coordinate(y=14, x=17)], 12|17=[Coordinate(y=11, x=17), Coordinate(y=13, x=17)], 11|17=[Coordinate(y=11, x=18), Coordinate(y=12, x=17)], 11|18=[Coordinate(y=11, x=19), Coordinate(y=11, x=17)], 11|19=[Coordinate(y=11, x=20), Coordinate(y=11, x=18)], 11|20=[Coordinate(y=11, x=21), Coordinate(y=11, x=19)], 13|12=[Coordinate(y=13, x=13), Coordinate(y=13, x=11)], 13|11=[Coordinate(y=13, x=12), Coordinate(y=13, x=10)], 13|10=[Coordinate(y=13, x=11), Coordinate(y=13, x=9)], 13|9=[Coordinate(y=13, x=10), Coordinate(y=14, x=9)], 14|9=[Coordinate(y=13, x=9), Coordinate(y=15, x=9)], 15|9=[Coordinate(y=14, x=9), Coordinate(y=15, x=8)], 15|8=[Coordinate(y=15, x=9), Coordinate(y=15, x=7)], 15|7=[Coordinate(y=14, x=7), Coordinate(y=15, x=8)], 14|7=[Coordinate(y=13, x=7), Coordinate(y=15, x=7)], 13|7=[Coordinate(y=14, x=7), Coordinate(y=13, x=6)], 13|6=[Coordinate(y=13, x=7), Coordinate(y=13, x=5)], 13|5=[Coordinate(y=12, x=5), Coordinate(y=13, x=6), Coordinate(y=14, x=5)], 12|5=[Coordinate(y=11, x=5), Coordinate(y=13, x=5)], 11|5=[Coordinate(y=12, x=5), Coordinate(y=11, x=4)], 11|4=[Coordinate(y=11, x=5), Coordinate(y=11, x=3)], 11|3=[Coordinate(y=11, x=4), Coordinate(y=12, x=3)], 12|3=[Coordinate(y=11, x=3), Coordinate(y=13, x=3)], 13|3=[Coordinate(y=12, x=3), Coordinate(y=13, x=2)], 13|2=[Coordinate(y=13, x=3), Coordinate(y=13, x=1)], 13|1=[Coordinate(y=12, x=1), Coordinate(y=13, x=2)], 12|1=[Coordinate(y=11, x=1), Coordinate(y=13, x=1)], 11|1=[Coordinate(y=10, x=1), Coordinate(y=12, x=1)], 10|1=[Coordinate(y=9, x=1), Coordinate(y=11, x=1)], 9|1=[Coordinate(y=9, x=2), Coordinate(y=10, x=1)], 9|2=[Coordinate(y=9, x=3), Coordinate(y=9, x=1)], 9|3=[Coordinate(y=9, x=4), Coordinate(y=9, x=2)], 9|4=[Coordinate(y=9, x=5), Coordinate(y=9, x=3)], 9|5=[Coordinate(y=8, x=5), Coordinate(y=9, x=4)], 8|5=[Coordinate(y=7, x=5), Coordinate(y=9, x=5)], 7|5=[Coordinate(y=8, x=5), Coordinate(y=7, x=4)], 7|4=[Coordinate(y=7, x=5), Coordinate(y=7, x=3)], 7|3=[Coordinate(y=6, x=3), Coordinate(y=7, x=4)], 6|3=[Coordinate(y=5, x=3), Coordinate(y=7, x=3)], 14|5=[Coordinate(y=13, x=5), Coordinate(y=15, x=5)], 15|5=[Coordinate(y=14, x=5), Coordinate(y=15, x=4)], 15|4=[Coordinate(y=15, x=5), Coordinate(y=15, x=3)], 15|3=[Coordinate(y=15, x=4), Coordinate(y=15, x=2)], 15|2=[Coordinate(y=15, x=3), Coordinate(y=15, x=1)], 15|1=[Coordinate(y=15, x=2), Coordinate(y=16, x=1)], 16|1=[Coordinate(y=15, x=1), Coordinate(y=17, x=1)], 17|1=[Coordinate(y=16, x=1), Coordinate(y=17, x=2)], 17|2=[Coordinate(y=17, x=3), Coordinate(y=17, x=1)], 17|3=[Coordinate(y=18, x=3), Coordinate(y=17, x=2)], 18|3=[Coordinate(y=17, x=3), Coordinate(y=19, x=3)], 19|3=[Coordinate(y=18, x=3), Coordinate(y=19, x=2)], 19|2=[Coordinate(y=19, x=3), Coordinate(y=19, x=1)], 19|1=[Coordinate(y=19, x=2), Coordinate(y=20, x=1)], 20|1=[Coordinate(y=19, x=1), Coordinate(y=21, x=1)], 21|1=[Coordinate(y=20, x=1), Coordinate(y=21, x=2)], 21|2=[Coordinate(y=21, x=3), Coordinate(y=21, x=1)], 21|3=[Coordinate(y=21, x=4), Coordinate(y=21, x=2)], 21|4=[Coordinate(y=21, x=5), Coordinate(y=21, x=3)], 21|5=[Coordinate(y=20, x=5), Coordinate(y=21, x=4)], 20|5=[Coordinate(y=19, x=5), Coordinate(y=21, x=5)], 19|5=[Coordinate(y=19, x=6), Coordinate(y=20, x=5)], 19|6=[Coordinate(y=19, x=7), Coordinate(y=19, x=5)], 19|7=[Coordinate(y=18, x=7), Coordinate(y=19, x=6)], 18|7=[Coordinate(y=17, x=7), Coordinate(y=19, x=7)], 17|7=[Coordinate(y=17, x=8), Coordinate(y=18, x=7)], 17|8=[Coordinate(y=17, x=9), Coordinate(y=17, x=7)], 17|9=[Coordinate(y=18, x=9), Coordinate(y=17, x=8)], 18|9=[Coordinate(y=17, x=9), Coordinate(y=19, x=9)], 19|9=[Coordinate(y=18, x=9), Coordinate(y=20, x=9)], 20|9=[Coordinate(y=19, x=9), Coordinate(y=21, x=9)], 21|9=[Coordinate(y=20, x=9), Coordinate(y=21, x=10)], 21|10=[Coordinate(y=21, x=11), Coordinate(y=21, x=9)], 21|11=[Coordinate(y=20, x=11), Coordinate(y=21, x=10)], 20|11=[Coordinate(y=19, x=11), Coordinate(y=21, x=11)], 19|11=[Coordinate(y=19, x=12), Coordinate(y=20, x=11)], 19|12=[Coordinate(y=19, x=13), Coordinate(y=19, x=11)]})
//
//Process finished with exit code 0