package day01

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("processing of test input") {
        forAll(
            row("Day01TestInput1.txt", emptyMap(), 142),
            row(
                "Day01TestInput2.txt", mapOf(
                    "one" to '1',
                    "two" to '2',
                    "three" to '3',
                    "four" to '4',
                    "five" to '5',
                    "six" to '6',
                    "seven" to '7',
                    "eight" to '8',
                    "nine" to '9'
                ), 281
            ),
        ) { filepath, mapping, expected ->
            Then(filepath) {
                filepath.filePathToStringList().sumOf { it.toInt(mapping) } shouldBe expected
            }
        }
    }
})
