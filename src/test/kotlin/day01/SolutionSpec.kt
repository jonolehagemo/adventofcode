package day01

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("processing of test input") {
        forAll(
            row("Day01TestInput1.txt",142),
            row("Day01TestInput2.txt", 281),
        ) { filepath, expected ->
            Then(filepath) {
                filepath.filePathToStringList().sumOf { it.deStringify().toInteger() } shouldBe expected
            }
        }
    }
})
