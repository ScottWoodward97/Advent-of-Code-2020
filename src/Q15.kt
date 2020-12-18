class Q15 {
    private val input: List<Int> = listOf(20, 0, 1, 11, 6, 3)

    fun partA(target: Int = 2020): Int {
        val numbers = input.toMutableList()
        while (numbers.size < target){
            val prevNum = numbers.last()
            numbers += if (numbers.indexOf(prevNum) == numbers.lastIndex)
                0
            else
                numbers.lastIndex - numbers.subList(0, numbers.lastIndex).lastIndexOf(prevNum)
        }
        return numbers.last()
    }

    //Not the fastest of solutions but better than using a list as in partA
    fun partB(target: Int = 30000000): Int {
        val inputMap = input.dropLast(1).mapIndexed{ ind, value -> value to ind}.toMap().toMutableMap()
        var recent = input.last()
        var i = input.lastIndex
        while (i < target - 1){
            if (recent in inputMap){
                val lastTime = inputMap[recent]!!
                inputMap[recent] = i
                recent = i - lastTime
            }else{
                inputMap[recent] = i
                recent = 0
            }
            i++
        }
        return recent
    }
}