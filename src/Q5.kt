import java.io.File

class Q5(filePath: String = "res/inputs/Q5.txt") {

    private val seats = File(filePath).readLines().map { Seat(it) }

    fun partA(): Int = seats.maxBy { it.seatId }?.seatId ?: 0

    fun partB(): Int = seats.map { it.seatId }.sorted().let {ids ->
        val range = ids[0]..ids[ids.lastIndex]
        range.filterNot { it in ids }.first()
    }

}


class Seat(binaryPass: String) {

    val row: Int
    val column: Int
    val seatId: Int

    init {
        row = getValue(binaryPass.substring(0, 7), 'F')
        column = getValue(binaryPass.substring(7, 10), 'L')
        seatId = (row * 8) + column
    }

    private fun getValue(binaryPass: String, zeroChar: Char): Int =
            binaryPass
                    .map { if (it == zeroChar) "0" else "1" }
                    .joinToString(separator = "")
                    .toInt(2)
}