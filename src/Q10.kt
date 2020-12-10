import java.io.File

class Q10(filePath: String = "res/inputs/Q10.txt") {
    private val jolts: List<Int> = File(filePath).readLines().map { it.toInt() }

    fun partA(): Int {
        val joltsChain = listOf(0) + jolts.sorted() + listOf(jolts.max()!! + 3)

        val diffs = joltsChain.subList(1, joltsChain.size).mapIndexed { ind, jolt ->
            jolt - joltsChain[ind]
        }

        return diffs.count { it == 1 } * diffs.count { it == 3 }
    }

    fun partB(): Long {
        val joltsChain = jolts.sorted() + listOf(jolts.max()!! + 3)

        val permutations = mutableMapOf<Int, Long>()
        permutations[0] = 1

        for (jolt in joltsChain) {
            permutations[jolt] = (permutations.tryGetValue(jolt - 1, 0) +
                    permutations.tryGetValue(jolt - 2, 0) +
                    permutations.tryGetValue(jolt - 3, 0))
        }
        return permutations.getValue(joltsChain.last())
    }

    private fun <K,V> Map<K, V>.tryGetValue(key: K, fallBack: V): V = try {
        getValue(key)
    }catch (e: NoSuchElementException){
        fallBack
    }

}