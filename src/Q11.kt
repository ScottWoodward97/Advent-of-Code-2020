import java.io.File

class Q11(filePath: String = "res/inputs/Q11.txt") {
    private val seats = File(filePath).readLines().map { it.toCharArray().toList() }

    fun partA(): Int {
        var prevSeats = seats
        var newSeats: List<List<Char>> = update(prevSeats)
        while (prevSeats != newSeats){
            prevSeats = newSeats
            newSeats = update(prevSeats)
        }
        return newSeats.flatten().count { it == '#' }
    }

    private fun update(seats: List<List<Char>>): List<List<Char>> {
        val numRows = seats.size
        val lenRows = seats.flatten().size / numRows

        fun numOccupied(index: Int): Int {
            val indX = index / lenRows
            val indY = index % lenRows
            val adjCoords = listOf(
                    Pair(indX + 1, indY + 1),
                    Pair(indX + 1, indY),
                    Pair(indX + 1, indY - 1),
                    Pair(indX, indY + 1),
                    Pair(indX, indY - 1),
                    Pair(indX - 1, indY + 1),
                    Pair(indX - 1, indY),
                    Pair(indX - 1, indY - 1)
            ).filter { it.first in 0 until numRows && it.second in 0 until lenRows }
            return adjCoords.map { seats[it.first][it.second] }.count { it == '#' }
        }

        return seats.flatten().mapIndexed { index, c ->
            when (c) {
                'L' -> if (numOccupied(index) == 0) '#' else 'L'
                '#' -> if (numOccupied(index) >= 4) 'L' else '#'
                else -> '.'
            }
        }.chunked(lenRows)
    }
}