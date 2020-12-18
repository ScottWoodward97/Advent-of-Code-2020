import java.io.File

class Q17(filePath: String = "res/inputs/Q17.txt") {
    private val gridInput = File(filePath).readLines().map { it.toList() }
    private val initGrid = gridInput.flatten().withIndex().map {
        Triple(it.index % gridInput.first().size, it.index / gridInput.first().size, 1) to (it.value == '#')
    }.toMap()

    fun partA(cycles: Int = 6): Int {
        var grid = initGrid.toMutableMap().withDefault { false }
        initGrid.keys.forEach { neighbours(it).forEach { if (it !in grid) grid[it] = false } }
        repeat(cycles) {
            val nextGrid = mutableMapOf<Triple<Int, Int, Int>, Boolean>()
            grid.forEach { (coord, active) ->
                val neighbours = neighbours(coord)
                val count = neighbours.map { grid[it] }.count { it == true }
                nextGrid[coord] = (active && count in 2..3) || (!active && count == 3)
                neighbours.forEach {
                    if (it !in nextGrid) nextGrid[it] = false
                }
            }
            grid = nextGrid
        }
        return grid.filterValues { it }.size
    }

    fun partB(cycles: Int = 6): Int{
        val expandedInitGrid = initGrid.map { Quadruple(it.key.first, it.key.second, it.key.third, 1) to it.value }.toMap()
        var grid = expandedInitGrid.toMutableMap().withDefault { false }
        expandedInitGrid.keys.forEach { neighbours(it).forEach { if (it !in grid) grid[it] = false } }
        repeat(cycles) {
            val nextGrid = mutableMapOf<Quadruple<Int, Int, Int, Int>, Boolean>()
            grid.forEach { (coord, active) ->
                val neighbours = neighbours(coord)
                val count = neighbours.map { grid[it] }.count { it == true }
                nextGrid[coord] = (active && count in 2..3) || (!active && count == 3)
                neighbours.forEach {
                    if (it !in nextGrid) nextGrid[it] = false
                }
            }
            grid = nextGrid
        }
        return grid.filterValues { it }.size
    }

    // too lazy to think of something better
    private fun neighbours(coord: Triple<Int, Int, Int>): List<Triple<Int, Int, Int>> = listOf(
            Triple(coord.first + 1, coord.second, coord.third),
            Triple(coord.first - 1, coord.second, coord.third),
            Triple(coord.first + 1, coord.second + 1, coord.third),
            Triple(coord.first + 1, coord.second - 1, coord.third),
            Triple(coord.first - 1, coord.second + 1, coord.third),
            Triple(coord.first - 1, coord.second - 1, coord.third),
            Triple(coord.first + 1, coord.second, coord.third + 1),
            Triple(coord.first + 1, coord.second, coord.third - 1),
            Triple(coord.first - 1, coord.second, coord.third + 1),
            Triple(coord.first - 1, coord.second, coord.third - 1),
            Triple(coord.first + 1, coord.second + 1, coord.third + 1),
            Triple(coord.first + 1, coord.second + 1, coord.third - 1),
            Triple(coord.first + 1, coord.second - 1, coord.third + 1),
            Triple(coord.first + 1, coord.second - 1, coord.third - 1),
            Triple(coord.first - 1, coord.second + 1, coord.third + 1),
            Triple(coord.first - 1, coord.second + 1, coord.third - 1),
            Triple(coord.first - 1, coord.second - 1, coord.third + 1),
            Triple(coord.first - 1, coord.second - 1, coord.third - 1),
            Triple(coord.first, coord.second + 1, coord.third),
            Triple(coord.first, coord.second - 1, coord.third),
            Triple(coord.first, coord.second + 1, coord.third + 1),
            Triple(coord.first, coord.second + 1, coord.third - 1),
            Triple(coord.first, coord.second - 1, coord.third + 1),
            Triple(coord.first, coord.second - 1, coord.third - 1),
            Triple(coord.first, coord.second, coord.third + 1),
            Triple(coord.first, coord.second, coord.third - 1)
    )

    private fun neighbours(coord: Quadruple<Int, Int, Int, Int>): List<Quadruple<Int, Int, Int, Int>> {
        fun foo(coord: Quadruple<Int, Int, Int, Int>,
                x: Boolean = false,
                y: Boolean = false,
                z: Boolean = false
        ): List<Quadruple<Int, Int, Int, Int>> = when {
            x -> foo(coord, y=true) + foo(coord.copy(first = coord.first + 1), y=true) + foo(coord.copy(first = coord.first - 1), y=true)
            y -> foo(coord, z=true) + foo(coord.copy(second = coord.second + 1), z=true) + foo(coord.copy(second = coord.second - 1), z=true)
            z -> foo(coord) + foo(coord.copy(third = coord.third + 1)) + foo(coord.copy(third = coord.third - 1))
            else -> listOf(coord, coord.copy(fourth = coord.fourth + 1), coord.copy(fourth = coord.fourth - 1))
        }
        return foo(coord, x=true).filterNot { it == coord }
    }

    data class Quadruple<A, B, C, D>(
            val first: A,
            val second: B,
            val third: C,
            val fourth: D
    )
}