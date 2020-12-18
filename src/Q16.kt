import java.io.File

class Q16(filePath: String = "res/inputs/Q16.txt") {
    private val inputs = File(filePath).readText().split("\n\n").map { it.split("\n") }
    private val rules = inputs[0].map {
        it.split(":").let {
            it[0] to it[1].split(" or ").map { it.split("-").let { it[0].trim().toInt()..it[1].trim().toInt() } }
        }
    }
    private val myTicket = inputs[1][1].split(",").map { it.toInt() }
    private val tickets = inputs[2].drop(1).map { it.split(",").map { it.toInt() } }


    fun partA(): Int {
        val ranges = rules.map { it.second }
        val invalidValues = tickets.map { it.filterNot { ranges.any { r -> r.contains(it) } } }
        return invalidValues.flatten().sum()
    }

    //What a mess of a solution this is
    fun partB(): Long {
        val ranges = rules.map { it.second }
        val validTickets = tickets.filter { it.all { ranges.any { r -> r.contains(it) } } }
        val fields = ranges.indices.map { validTickets.map { m -> m[it] } }
        val validFieldMatrix = ranges.map { range -> fields.map { field -> field.all { range.contains(it) } } }

        val mappedTicket: MutableList<Pair<String, Int?>> = rules.map { it.first to null }.toMutableList()
        val maskedIndices = mutableListOf<Pair<Int, Int>>()
        while (mappedTicket.any { it.second == null }) {
            validFieldMatrix.asSequence().withIndex()
                    .filterNot{ it.index in maskedIndices.map { it.first }}
                    .map { it.copy( value = it.value.filterIndexed { ind, i -> !(ind in maskedIndices.map { it.second }) } ) }
                    .filter { list -> list.value.count { it } == 1 }
                    .map { it.index }.toList()
                    .forEach {fieldInd ->
                        val ind = validFieldMatrix[fieldInd].withIndex().filter { it.value }.map { it.index }.subtract(maskedIndices.map { it.second }).first() //only one
                        mappedTicket[fieldInd] = mappedTicket[fieldInd].copy(second = myTicket[ind])
                        maskedIndices += Pair(fieldInd, ind)
                    }
        }
        return mappedTicket.filter { it.first.startsWith("departure") }.map { it.second!!.toLong() }.reduce{ acc, i -> acc * i }
    }

    private fun List<IntRange>.contains(value: Int): Boolean = this.any { value in it }

}