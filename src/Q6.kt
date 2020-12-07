import java.io.File

class Q6(filePath: String = "res/inputs/Q6.txt") {
    private val answerGroups: List<List<String>> = File(filePath).readText().split("\n\n").map { it.split("\n") }

    fun partA(): Int =
            answerGroups.map { group ->
                group.reduceRight { str, acc ->
                    acc.toCharArray().union(str.toCharArray().asIterable()).joinToString(separator = "")
                }
            }.sumBy { it.length }

    fun partB(): Int =
            answerGroups.map { group ->
                group.reduceRight { str, acc ->
                    acc.toCharArray().intersect(str.toCharArray().asIterable()).joinToString(separator = "")
                }
            }.sumBy { it.length }

}