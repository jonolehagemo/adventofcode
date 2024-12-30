package aoc2024.day09

import extensions.filePathToString
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("test input, toBlocks()") {
        forAll(
            row(
                "12345",
                listOf(Block(0, 1), null, null, Block(1, 3), Block(1, 3), Block(1, 3), null, null, null, null, Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5))
            ),
            row(
                "2333133121414131402",
                listOf(Block(0, 2), Block(0, 2), null, null, null, Block(1, 3), Block(1, 3), Block(1, 3), null, null, null, Block(2, 1), null, null, null, Block(3, 3), Block(3, 3), Block(3, 3), null, Block(4, 2), Block(4, 2), null, Block(5, 4), Block(5, 4), Block(5, 4), Block(5, 4), null, Block(6, 4), Block(6, 4), Block(6, 4), Block(6, 4), null,Block(7, 3),Block(7, 3),Block(7, 3), null,Block(8, 4),Block(8, 4),Block(8, 4),Block(8, 4),Block(9, 2),Block(9, 2))
            )
        ) { input, expected ->
            When(input) {
                val result = input.toBlocks()

                Then("result should be expected") {
                    result shouldBe expected
                }
            }
        }
    }

    Given("test input, deFragment1()") {
        forAll(
            row(
                listOf(Block(0, 1), null, null, Block(1, 3), Block(1, 3), Block(1, 3), null, null, null, null, Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5)),
                listOf(Block(0, 1), Block(2, 5), Block(2, 5), Block(1, 3), Block(1, 3), Block(1, 3), Block(2, 5), Block(2, 5), Block(2, 5), null, null, null, null, null, null),
            ),
            row(
                listOf(Block(0, 2), Block(0, 2), null, null, null, Block(1, 3), Block(1, 3), Block(1, 3), null, null, null, Block(2, 1), null, null, null, Block(3, 3), Block(3, 3), Block(3, 3), null, Block(4, 2), Block(4, 2), null, Block(5, 4), Block(5, 4), Block(5, 4), Block(5, 4), null, Block(6, 4), Block(6, 4), Block(6, 4), Block(6, 4), null,Block(7, 3),Block(7, 3),Block(7, 3), null,Block(8, 4),Block(8, 4),Block(8, 4),Block(8, 4),Block(9, 2),Block(9, 2)),
                listOf(Block(0, 2), Block(0, 2),Block(9, 2),Block(9, 2),Block(8, 4), Block(1, 3), Block(1, 3), Block(1, 3),Block(8, 4),Block(8, 4),Block(8, 4), Block(2, 1),Block(7, 3),Block(7, 3),Block(7, 3), Block(3, 3), Block(3, 3), Block(3, 3), Block(6, 4), Block(4, 2), Block(4, 2), Block(6, 4), Block(5, 4), Block(5, 4), Block(5, 4), Block(5, 4), Block(6, 4), Block(6, 4), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            ),
        ) { input, expected ->
            When(input.toString()) {
                val result = input.deFragment1()

                Then("result should be expected") {
                    result shouldBe expected
                }
            }
        }
    }

    Given("test input, deFragment2()") {
        forAll(
            row(
                listOf(Block(0, 1), null, null, Block(1, 3), Block(1, 3), Block(1, 3), null, null, null, null, Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5)),
                listOf(Block(0, 1), null, null, Block(1, 3), Block(1, 3), Block(1, 3), null, null, null, null, Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5), Block(2, 5)),
            ),
            row(
                listOf(Block(0, 2), Block(0, 2), null, null, null, Block(1, 3), Block(1, 3), Block(1, 3), null, null, null, Block(2, 1), null, null, null, Block(3, 3), Block(3, 3), Block(3, 3), null, Block(4, 2), Block(4, 2), null, Block(5, 4), Block(5, 4), Block(5, 4), Block(5, 4), null, Block(6, 4), Block(6, 4), Block(6, 4), Block(6, 4), null,Block(7, 3),Block(7, 3),Block(7, 3), null,Block(8, 4),Block(8, 4),Block(8, 4),Block(8, 4),Block(9, 2),Block(9, 2)),
                listOf(Block(0, 2), Block(0, 2),Block(9, 2),Block(9, 2), Block(2, 1), Block(1, 3), Block(1, 3), Block(1, 3),Block(7, 3),Block(7, 3),Block(7, 3),null, Block(4, 2), Block(4, 2), null, Block(3, 3), Block(3, 3), Block(3, 3), null,null,null, null, Block(5, 4), Block(5, 4), Block(5, 4), Block(5, 4), null, Block(6, 4), Block(6, 4), Block(6, 4), Block(6, 4), null,null,null,null, null,Block(8, 4),Block(8, 4),Block(8, 4),Block(8, 4), null, null),
            ),
        ) { input, expected ->
            When(input.toString()) {
                val result = input.deFragment2()

                Then("result should be expected") {
                    result shouldBe expected
                }
            }
        }
    }

    Given("test input, checksum()") {
        forAll(
            row(
                listOf(Block(0, 1), Block(2, 5), Block(2, 5), Block(1, 3), Block(1, 3), Block(1, 3), Block(2, 5), Block(2, 5), Block(2, 5), null, null, null, null, null, null),
                60
            ),
            row(
                listOf(Block(0, 2), Block(0, 2),Block(9, 2),Block(9, 2),Block(8, 4), Block(1, 3), Block(1, 3), Block(1, 3),Block(8, 4),Block(8, 4),Block(8, 4), Block(2, 1),Block(7, 3),Block(7, 3),Block(7, 3), Block(3, 3), Block(3, 3), Block(3, 3), Block(6, 4), Block(4, 2), Block(4, 2), Block(6, 4), Block(5, 4), Block(5, 4), Block(5, 4), Block(5, 4), Block(6, 4), Block(6, 4), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                1928
            )
        ) { input, expected ->
            When(input.toString()) {
                val result = input.checksum()

                Then("result should be expected") {
                    result shouldBe expected
                }
            }
        }
    }

    Given("task 1") {
        forAll(
            row("aoc2024/Day09TestInput1.txt", 1928L),
            row("aoc2024/Day09Input.txt", 6360094256423L),
        ) { filepath, expected ->
            When(filepath) {
                val result = filepath.filePathToString()
                    .toBlocks()
                    .deFragment1()
                    .checksum()

                Then("Task 1: $result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }

    Given("task 2") {
        forAll(
            row("aoc2024/Day09TestInput1.txt", 2858L),
            row("aoc2024/Day09Input.txt", 6379677752410L),
        ) { filepath, expected ->
            When(filepath) {
                val result = filepath.filePathToString()
                    .toBlocks()
                    .deFragment2()
                    .checksum()

                Then("Task 2: $result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }
})
