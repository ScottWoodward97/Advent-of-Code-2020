import java.io.File

class Q4(filePath: String = "res/inputs/Q4.txt"){
    private val passports = File(filePath).readText().split("\n\n").map { Passport.from(it) }

    fun partA(): Int = passports.filter { it.isPartAValid() }.size

    fun partB(): Int = passports.filter { it.isPartBValid() }.size
}

data class Passport(
        val byr: String?,
        val iyr: String?,
        val eyr: String?,
        val hgt: String?,
        val hcl: String?,
        val ecl: String?,
        val pid: String?,
        val cid: String?
) {
    fun isPartAValid(): Boolean = listOf(byr, iyr, eyr, hgt, hcl, ecl, pid).all { it != null }

    fun isPartBValid(): Boolean {
        return isPartAValid() &&
                byr?.toInt() in 1920..2002 &&
                iyr?.toInt() in 2010..2020 &&
                eyr?.toInt() in 2020..2030 &&
                hgt?.let {
                    (it.contains("cm") && it.removeSuffix("cm").toInt() in 150..193) ||
                            (it.contains("in") && it.removeSuffix("in").toInt() in 59..76)
                } == true &&
                hcl?.matches(Regex("(#[0-9,a-f]{6})")) == true &&
                ecl in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") &&
                pid?.matches(Regex("[0-9]{9}")) == true
    }


    companion object {
        fun from(batch: String): Passport =
                batch.split(Regex("\\s")).associate { attr ->
                    attr.split(":").let { Pair(it[0], it[1]) }
                }.let {
                    Passport(
                            it["byr"],
                            it["iyr"],
                            it["eyr"],
                            it["hgt"],
                            it["hcl"],
                            it["ecl"],
                            it["pid"],
                            it["cid"]
                    )
                }
    }
}