package aoc2024.day12

import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("Tasks") {
            forAll(
                row("aoc2024/Day12TestInput1.txt", 140, 80),
                row("aoc2024/Day12TestInput2.txt", 1930, 1206),
                row("aoc2024/Day12Input.txt", 1473408, 886364),
            ) { filepath, expectedPrice, expectedDiscountedPrice ->
                When(filepath) {
                    val input = filepath.filePathToGrid(defaultValue = ' ')
                    val regions = input.toRegions()

                    Then("Task 1, Price: ${regions.sumOf { it.area * it.perimeter }} should be $expectedPrice") {
                        regions.sumOf { it.area * it.perimeter } shouldBe expectedPrice
                    }

                    Then("Task 2, Discounted Price: ${regions.sumOf { it.area * it.sides }} should be $expectedDiscountedPrice") {
                        regions.sumOf { it.area * it.sides } shouldBe expectedDiscountedPrice
                    }
                }
            }
        }
    })
