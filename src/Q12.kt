import java.io.File
import kotlin.math.abs

class Q12(filePath: String = "res/inputs/Q12.txt") {
    private val instructions = File(filePath).readLines().map { it.first() to it.substring(1).toInt() }

    fun partA(): Int {
        var ship = Pair(0,0)
        var deg = 90 // East

        fun deg2Dir(): Char = when (deg) {
            0 -> 'N'
            90 -> 'E'
            180 -> 'S'
            else -> 'W'
        }
        val convertedInstructions = instructions.map {
            when (it.first) {
                'L' -> deg = Math.floorMod(deg - it.second, 360)
                'R' -> deg = Math.floorMod(deg + it.second, 360)
            }
            //converting F to N,E,S,W
            when (it.first) {
                'F' -> Pair(deg2Dir(), it.second)
                else -> it
            }
        }

        convertedInstructions.forEach {
            when (it.first) {
                'N' -> ship = ship.copy(first = ship.first + it.second)
                'S' -> ship = ship.copy(first = ship.first - it.second)
                'E' -> ship = ship.copy(second = ship.second + it.second)
                'W' -> ship = ship.copy(second = ship.second - it.second)
            }
        }

        return abs(ship.first) + abs(ship.second)
    }

    fun partB(): Int {
        var wayPoint = Pair(1, 10)
        var ship = Pair(0, 0)

        instructions.forEach {
            when (it.first) {
                'N' -> wayPoint = wayPoint.copy(first = wayPoint.first + it.second)
                'S' -> wayPoint = wayPoint.copy(first = wayPoint.first - it.second)
                'E' -> wayPoint = wayPoint.copy(second = wayPoint.second + it.second)
                'W' -> wayPoint = wayPoint.copy(second = wayPoint.second - it.second)
                'R' -> repeat(it.second/90){wayPoint = Pair(-wayPoint.second, wayPoint.first)}
                'L' -> repeat(it.second/90){wayPoint = Pair(wayPoint.second, -wayPoint.first)}
                'F' -> ship = Pair(ship.first + (wayPoint.first * it.second), ship.second + (wayPoint.second * it.second))
            }
        }

        return abs(ship.first) + abs(ship.second)
    }

}