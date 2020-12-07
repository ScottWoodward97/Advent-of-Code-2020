import java.io.File

class Q3(filePath: String = "res/inputs/Q3.txt"){
    val map = TobogganMap.from(
            File(filePath).readLines()
    )

    fun partA(xstep: Int = 3, ystep: Int = 1): Int{

        val numrows = map.trees.size
        val numcolumns = map.trees.first().size
        //num coords = numrows/ystep

        val xcoords = (0 until numrows).map { (it * xstep) % numcolumns }
        val ycoords = (0 until numrows).map { it * ystep }.filter { it < map.trees.size }

        val coords = xcoords.zip(ycoords)
        return coords.filter { map.trees[it.second][it.first] }.size

    }

    fun partB(): Int = partA(1,1) * partA() * partA(5,1) * partA(7,1) * partA(1,2)
}


data class TobogganMap(
        val trees: List<List<Boolean>>
) {
    companion object {
        fun from(map: List<String>): TobogganMap = TobogganMap(map.map { row -> row.map { it == '#' } })
    }
}