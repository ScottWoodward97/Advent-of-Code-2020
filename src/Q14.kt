import java.io.File

class Q14(filePath: String = "res/inputs/Q14.txt") {
    private val instructions = File(filePath).readLines()

    fun partA(): Long {
        var mask = ""
        val memory = mutableMapOf<Int, Long>().withDefault { 0 }

        fun mask(value: Int): Long {
            val padded = Integer.toBinaryString(value).padStart(36, padChar = '0').toMutableList()
            mask.withIndex().filter { it.value != 'X' }.forEach { padded[it.index] = it.value }
            return padded.joinToString("").toLong(2)
        }

        instructions.forEach { instruction ->
            if (instruction.contains("mask = ")) {
                mask = instruction.substringAfter("mask = ")
            } else {
                instruction.split(" = ").let {
                    memory[it.first().filter { it.isDigit() }.toInt()] = mask(it.last().toInt())
                }
            }
        }
        return memory.values.sum()
    }

    fun partB(): Long {
        var mask = ""
        val memory = mutableMapOf<Long, Long>().withDefault { 0 }

        fun mask(value: Int): MutableList<Char> {
            val padded = Integer.toBinaryString(value).padStart(36, padChar = '0').toMutableList()
            mask.withIndex().filter { it.value != '0' }.forEach { padded[it.index] = it.value }
            return padded
        }

        fun expandFloating(floatingMask: MutableList<Char>): List<Long> {
            return if (!floatingMask.contains('X')) {
                listOf(floatingMask.joinToString("").toLong(2))
            } else {
                val ind = floatingMask.indexOfFirst { it == 'X' }
                expandFloating(floatingMask.mapIndexed { index, c -> if (index == ind) '1' else c }.toMutableList()) +
                        expandFloating(floatingMask.mapIndexed { index, c -> if (index == ind) '0' else c }.toMutableList())
            }
        }

        instructions.forEach { instruction ->
            if (instruction.contains("mask = ")) {
                mask = instruction.substringAfter("mask = ")
            } else {
                instruction.split(" = ").let {
                    val maskedInts = expandFloating(mask(it.first().filter { it.isDigit() }.toInt()))
                    maskedInts.forEach { key ->
                        memory[key] = it.last().toLong()
                    }
                }
            }
        }
        return memory.values.sum()

    }

}