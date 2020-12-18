import java.io.File

class Q18(filePath: String = "res/inputs/Q18.txt") {
    private val strings = File(filePath).readLines()

    fun partA(): Long = strings.map { evaluate(it) }.sum()

    fun partB(): Long = strings.map { evaluate(it, advanced = true) }.sum()

    private fun evaluate(expression: String, advanced: Boolean = false): Long {
        val bracketRegex = Regex("(\\((?:[^\\(]*?\\)))")
        var exp = expression
        while (exp.contains(Regex("[\\(\\)]+"))) {
            val deepestBrackets = bracketRegex.findAll(exp)
                    .map { it.range to evaluateBracketless(it.value.trim('(', ')'), advanced) }
            deepestBrackets.toList().asReversed().forEach {
                exp = exp.replaceRange(it.first, it.second.toString())
            }
        }
        return evaluateBracketless(exp, advanced)
    }

    //Evaluate with no brackets
    private fun evaluateBracketless(expression: String, advanced: Boolean = false): Long {
        fun Long.addOrMultiply(operator: String, value: Long): Long = when (operator) {
            "+" -> this + value
            "*" -> this * value
            else -> throw Exception("Not + or *")
        }
        var exp = expression
        if (advanced) {
            val plusRegex = Regex("(\\d+ \\+ \\d+)")
            while (exp.contains("+")) {
                exp = plusRegex.replace(exp) {
                    it.value.split(" + ").map { it.toLong() }.sum().toString()
                }
            }
        }
        var total = 0L
        val elements = exp.split(" ")
        total += elements[0].toLong()
        elements.drop(1).chunked(2).forEach {
            total = total.addOrMultiply(it[0], it[1].toLong())
        }
        return total
    }
}