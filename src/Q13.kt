import java.io.File
import java.math.BigInteger

class Q13(filePath: String = "res/inputs/Q13-test.txt") {
    private val arrival: Int
    private val timetable: List<String>

    init {
        File(filePath).readLines().let {
            arrival = it.first().toInt()
            timetable = it[1].split(",")
        }
    }

    fun partA(): Int {
        val availableBuses = timetable.filterNot { it == "x" }.map { it.toInt() }
        val waitTimes = availableBuses.map { it to it - (arrival % it) }
        return waitTimes.minBy { it.second }?.let { it.first * it.second } ?: 0
    }

    fun partB(): BigInteger {
        //Solution using chinese remainder theorem
        val buses = timetable.withIndex().filterNot { it.value == "x" }.map {
            when {
                it.index == 0 -> 0L
                it.value.toLong() - it.index < 0 -> Math.floorMod(it.value.toLong() - it.index, it.value.toLong())
                else -> it.value.toLong() - it.index
            } to it.value.toLong()
        }
        val crt = buses.mapIndexed { ind, _ ->
            buses.filterIndexed { i, _ -> i != ind }.map { it.second }.reduce { acc, l -> acc * l }.toBigInteger()
        }.mapIndexed { index, x ->
            var y = BigInteger.ONE
            while ((x * y) % buses[index].second.toBigInteger() != buses[index].first.toBigInteger()) {
                y = y.inc()
            }
            x * y
        }.reduce { total, e -> total + e }
        return crt % buses.map { it.second }.reduce { acc, l -> acc * l }.toBigInteger()
    }

}