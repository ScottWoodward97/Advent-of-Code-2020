import java.io.File

class Q9(filePath: String = "res/inputs/Q9.txt") {
    private val input = File(filePath).readLines().map { it.toLong() }

    fun partA(): Long {
        val preambleLength = 25
        var ind = 0
        while (canSum(input.subList(ind, preambleLength + ind), input[preambleLength + ind++])){}
        return input[preambleLength + --ind]
    }

    private fun canSum(numbers: List<Long>, total: Long): Boolean {
        numbers.sorted().let {
            it.forEachIndexed { ind, num ->
                if (it.subList(ind + 1, it.size).contains(total - num)) return true
            }
        }
        return false
    }

    fun partB(): Long {
        val invalid = partA()
        var sumList: List<Long>?
        var i = 0
        do {
            sumList = consecSum(input, invalid, i++)
        } while (sumList == null)
        return (sumList.min() ?: 0) + (sumList.max() ?: 0)
    }

    private fun consecSum(list: List<Long>, total: Long, startInd: Int = 0): List<Long>? {
        var i = startInd
        while (list.subList(startInd, i + 1).sum() <= total) {
            if (list.subList(startInd, i + 1).sum() == total) return list.subList(startInd, i + 1)
            i++
        }
        return null
    }


}