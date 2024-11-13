package aoc2022.day13

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1, firstToken()") {
            forAll(
                row("1", "[1,10,100]", "["),
                row("2", "1,10,100]", "1"),
                row("3", ",10,100]", ","),
                row("4", "10,100]", "10"),
                row("5", "100]", "100"),
                row("6", "]", "]"),
            ) { line, s, expected ->
                When("line $line -> $expected") {
                    val result = s.firstToken()

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 1, compare()") {
            forAll(
                row("1", "[1,1,3,1,1]", "[1,1,5,1,1]", -1),
                row("2", "[[1],[2,3,4]]", "[[1],4]", -1),
                row("3", "[9]", "[[8,7,6]]", 1),
                row("4", "[[4,4],4,4]", "[[4,4],4,4,4]", -1),
                row("5", "[7,7,7,7]", "[7,7,7]", 1),
                row("6", "[]", "[3]", -1),
                row("7", "[[[]]]", "[[]]", 1),
                row("8", "[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]", 1),
            ) { line, a, b, expected ->
                When("line $line -> $expected") {
                    val result = compare(a, b)

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 1, answer") {
            forAll(
                row("aoc2022/Day13TestInput1.txt", 13),
                row("aoc2022/Day13Input.txt", 6240),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val input = filepath.filePathToStringList().filter { it.isNotBlank() }.asSequence()
                    val result =
                        input
                            .chunked(2)
                            .withIndex()
                            .map { (index, list) -> IndexedValue(index, compare(list[0], list[1])) }
                            .filter { (_, value) -> value == -1 }
                            .sumOf { it.index + 1 }

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2, answer") {
            forAll(
                row("aoc2022/Day13TestInput1.txt", 140),
                row("aoc2022/Day13Input.txt", 23142),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val input = filepath.filePathToStringList().filter { it.isNotBlank() }.asSequence()
                    val added = sequenceOf("[[2]]", "[[6]]")
                    val result =
                        input
                            .plus(added)
                            .sortedWith { a, b -> compare(a, b) }
                            .withIndex()
                            .filter { it.value in added }
                            .map { it.index + 1 }
                            .reduce { a, b -> a * b }

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })
