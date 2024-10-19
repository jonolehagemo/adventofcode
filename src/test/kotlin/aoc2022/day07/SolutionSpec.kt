package aoc2022.day07

import extensions.filePathToStringList
import extensions.println
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("test input for task 1") {
            forAll(
                row("aoc2022/Day07TestInput1.txt", 95437),
//                row("aoc2022/Day07Input.txt", 1443806),
            ) { filePath, expectedSize ->
                When("$filePath -[size]-> $expectedSize") {
                    val fileSystem =
                        filePath.filePathToStringList().toFileSystem()
                    val result =
                        fileSystem.findDirectoriesBelowThreshold(100_000)

                    Then("$result -> $expectedSize") {
                        result shouldBe expectedSize
                    }
                }
            }
        }

        Given("test input for task 2") {
            forAll(
                row("aoc2022/Day07TestInput1.txt", 24933642),
//                row("aoc2022/Day07Input.txt", 942298),
            ) { filePath, expectedSize ->
                When("$filePath -[needed]-> $expectedSize") {
                    val fileSystem =
                        filePath.filePathToStringList().toFileSystem()
                    val spaceNeeded = (70_000_000 - 30_000_000 - fileSystem.totalSize) * -1
                    spaceNeeded.println()
                    val result =
                        fileSystem.findSmallestDirectoryOverThreshold(spaceNeeded)

                    Then("$result -> $expectedSize") {
                        result shouldBe expectedSize
                    }
                }
            }
        }
    })

/*
Directory(
    subDirectories={
        /=Directory(
            subDirectories={
                a=Directory(
                    subDirectories={
                        e=Directory(
                            subDirectories={},
                            files={i=584},
                            size=584
                            )
                        },
                    files={
                        f=29116,
                        g=2557,
                        h.lst=62596
                    },
                    size=94853
                ),
                d=Directory(
                    subDirectories={},
                    files={
                        j=4060174,
                        d.log=8033020,
                        d.ext=5626152,
                        k=7214296
                    },
                    size=24933642
                )
            },
            files={
                b.txt=14848514,
                c.dat=8504156
            },
            size=23446939
        )
    },
    files={},
    size=23352670
)

 */
