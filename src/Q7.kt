import java.io.File

class Q7(filePath: String = "res/inputs/Q7.txt") {
    private val bags = File(filePath).readLines().map { Bag.from(it) }

    fun partA(): Int {
        var containers: List<Bag> = listOf()

        var newContainers: List<Bag> = bags.filter { it.contents.containsKey("shiny gold") }

        while (newContainers.isNotEmpty()) {
            containers = containers.union(newContainers).toList()
            newContainers = newContainers.flatMap { bag ->
                bags.filter { it.contents.containsKey(bag.colour) }
            }.toSet().toList()
        }

        return containers.size
    }

    fun partB(bagColour: String = "shiny gold"): Int {
        val bag = bags.find { it.colour == bagColour } ?: throw Error("$bagColour not found")
        var numExtraBags = 0

        bag.contents.forEach {
            numExtraBags += it.value * (partB(it.key) + 1)
        }

        return numExtraBags
    }
}

data class Bag(
        val colour: String,
        val contents: Map<String, Int>
) {
    companion object {
        fun from(rule: String): Bag {
            rule.split("contain").let {
                return Bag(
                        it.first().trim().removeSuffix(" bags"),
                        stringToBagMap(it[1].trim())
                )
            }
        }

        private fun stringToBagMap(bags: String): Map<String, Int> =
                if (bags == "no other bags.") emptyMap()
                else
                    bags.split(",", ".")
                            .filterNot { it.isBlank() }
                            .map { if (it.contains("bags")) it.removeSuffix(" bags") else it.removeSuffix(" bag") }
                            .map { bag ->
                                Pair(
                                        bag.filterNot { it.isDigit() }.trim(),
                                        bag.filter { it.isDigit() }.toInt()
                                )
                            }
                            .toMap()
    }
}