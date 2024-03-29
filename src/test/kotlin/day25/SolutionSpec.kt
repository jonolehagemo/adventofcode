package day25

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({

    Given("valid input") {
        forAll(
            row("Test input", "Day25TestInput1.txt", 15),
//            row("Test input", "Day25Input.txt", 1475),
        ) { description, filePath, expected ->
            When(description) {
                val graph = getInput(filePath)

                Then("graph should contain $expected nodes") {
                    graph.adjacencyList.keys.size shouldBe expected
                }
            }
        }
    }

    Given("valid graph") {
        forAll(
            row(
                "Test input", "Day25TestInput1.txt", 15
            )
        ) { description, filePath, expected ->
            When(description) {
                val graph = getInput(filePath)
                val nodes = graph.adjacencyList.keys.toList().sorted()
                val start = nodes.first()
                val finish = nodes.last()
                val shortestPathTree = graph.dijkstra(start)
//            val shortestPathList = graph.shortestPath(shortestPathTree, start, finish)
//            println("$start -> $finish = $shortestPathList")

                Then("graph should contain 15 nodes") {
                    shortestPathTree.keys.size shouldBeGreaterThan 0
                }

//                Then("create cypher vertices and edges") {
//                    print("CREATE " + nodes.joinToString(",") { "($it:Node {name: '$it'})" })
//                    print( graph.adjacencyList
//                        .flatMap { entry ->
//                            entry.value
//                                .map { (dest, _) ->
//                                    if (entry.key < dest)
//                                        entry.key to dest
//                                    else
//                                        dest to entry.key
//                                }
//                        }
//                        .distinct()
//                        .sortedWith ( compareBy({it.first}, {it.second}) )
//                        .joinToString(" ") { (from, to) ->
//                            ", ($from)-[:CONNECTS_TO]->($to)"
//                        }
//                    )
//                }


            }
        }
    }

    Given("process") {
        forAll(
            row("Test input", "Day25TestInput1.txt", 54),
        ) { description, filePath, expected ->
            When(description) {
                val graph = getInput(filePath)
                val result = process(graph)

                Then("result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }

})
