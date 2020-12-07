import java.io.File

class Q1 (reportPath: String = "res/inputs/Q1-a.txt") {

    private val report: List<Int> = File(reportPath).readLines().map { it.toInt() }.sortedDescending()

    fun partA(): Int{
        report.forEach {
            val rem = 2020 - it
            if (report.contains(rem)) return it * rem
        }
        return -1
    }

    fun partB(): Long{
        report.asReversed().forEachIndexed { ind1, val1 ->
            val max1 = 2020 - val1
            report.subList(ind1+1, report.size).filter { it <= max1 }.forEachIndexed { ind2, val2 ->
                val max2 = max1 - val2
                report.subList(ind2+1, report.size).filter { it <= max2 }.forEach {
                    if (val1 + val2 + it == 2020) return (val1*val2*it).toLong()
                }
            }
        }
        return -1L
    }

}


