import java.io.File

class Q2(filePath: String = "res/inputs/Q2.txt") {

    private val entries: List<Entry> = File(filePath).readLines().map { Entry.from(it) }

    fun partA(): Int = entries.filter { it.isPartAValid() }.size

    fun partB(): Int = entries.filter { it.isPartBValid() }.size

}

data class Entry(
        private val firstVal: Int,
        private val secondVal: Int,
        private val reqChar: Char,
        private val password: String
) {
    fun isPartAValid(): Boolean = password.count { it == reqChar } in firstVal..secondVal

    fun isPartBValid(): Boolean = (password[firstVal - 1] == reqChar) xor (password[secondVal - 1] == reqChar)

    companion object {
        fun from(entry: String): Entry {
            val attr = entry.split(":", "-", " ").filterNot { it.isBlank() }
            return Entry(
                    attr[0].toInt(),
                    attr[1].toInt(),
                    attr[2].first(),
                    attr[3]
            )
        }
    }
}
