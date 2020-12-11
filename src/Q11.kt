import java.io.File

class Q11(filePath: String = "res/inputs/Q11.txt") {
    private val seats = File(filePath).readLines().map { it.toCharArray().toList() }

    fun partA(): Int {
        var prevSeats = seats
        var newSeats: List<List<Char>> = updateA(prevSeats)
        while (prevSeats != newSeats) {
            prevSeats = newSeats
            newSeats = updateA(prevSeats)
        }
        return newSeats.flatten().count { it == '#' }
    }

    fun partB(): Int {
        var prevSeats = seats
        var newSeats: List<List<Char>> = updateB(prevSeats)
        while (prevSeats != newSeats) {
            prevSeats = newSeats
            newSeats = updateB(prevSeats)
        }
        return newSeats.flatten().count { it == '#' }
    }

    private fun updateA(seats: List<List<Char>>): List<List<Char>> {
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

    private fun updateB(seats: List<List<Char>>): List<List<Char>> {
        val numRows = seats.size
        val lenRows = seats.flatten().size / numRows

        fun visibleOccupied(index: Int): Int {
            val indX = index / lenRows
            val indY = index % lenRows

            val xaxis = seats[indX].withIndex().filterNot { it.index == indY || it.value == '.' }.partition { it.index > indY }.let { listOfNotNull(it.first.firstOrNull()?.value, it.second.lastOrNull()?.value) }
            val yaxis = seats.map { it[indY] }.withIndex().filterNot { it.index == indX || it.value == '.' }.partition { it.index > indX }.let { listOfNotNull(it.first.firstOrNull()?.value, it.second.lastOrNull()?.value) }

            fun diagCheck(xIncr: Int, yIncr: Int): Char {
                var x = indX + xIncr
                var y = indY + yIncr
                while ((x in 0 until numRows && y in 0 until lenRows) && seats[x][y] == '.') {
                    x+=xIncr
                    y+=yIncr
                }
                return if (x in 0 until numRows && y in 0 until lenRows) seats[x][y] else '.'

            }
            val diags = listOf(diagCheck(-1,-1), diagCheck(-1,+1), diagCheck(+1,+1), diagCheck(+1,-1))

            return (xaxis + yaxis + diags).count { it == '#' }
        }

        return seats.flatten().mapIndexed { index, c ->
            when (c) {
                'L' -> if (visibleOccupied(index) == 0) '#' else 'L'
                '#' -> if (visibleOccupied(index) >= 5) 'L' else '#'
                else -> '.'
            }
        }.chunked(lenRows)
    }
}