import java.io.File

class Q8(filePath: String = "res/inputs/Q8.txt") {

    private val instructions = File(filePath).readLines().map { Instruction.from(it) }

    fun partA(): Int = execute(instructions)

    fun partB(): Int {
        var accumulator = 0
        for (i in instructions.indices) {
            if (instructions[i].operation != Operation.ACC) {
                val newInstr = instructions.mapIndexed { index, instruction ->
                    if (index == i) instruction.copy(operation = instruction.operation.flip()) else instruction
                }
                try {
                    accumulator = execute(newInstr, throwIfInfinite = true)
                } catch (e: Error) {
                    continue
                }
            }
        }
        return accumulator
    }

    private fun execute(instructions: List<Instruction>, throwIfInfinite: Boolean = false): Int {
        var accumulator = 0
        val exeInstr = instructions.map { it to false }.toMutableList()

        var ind = 0
        var instr = exeInstr[ind]
        while (!instr.second) {
            exeInstr[ind] = Pair(instr.first, true)
            when (instr.first.operation) {
                Operation.ACC -> {
                    accumulator += instr.first.argument
                    ind++
                }
                Operation.JMP -> ind += instr.first.argument
                else -> ind++
            }
            if (ind > instructions.lastIndex) break
            instr = exeInstr[ind]
        }
        if (throwIfInfinite && instr.second) throw Error("Infinite Loop")
        return accumulator
    }

}

data class Instruction(
        val operation: Operation,
        val argument: Int
) {
    companion object {
        fun from(textInstruction: String): Instruction =
                textInstruction.split(" ").let {
                    Instruction(Operation.valueOf(it[0].toUpperCase()), it[1].toInt())
                }
    }
}

enum class Operation {
    ACC, JMP, NOP
}

private fun Operation.flip(): Operation = when (this){
    Operation.JMP -> Operation.NOP
    Operation.NOP -> Operation.JMP
    Operation.ACC -> Operation.ACC
}