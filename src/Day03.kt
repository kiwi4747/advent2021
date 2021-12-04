fun main() {

    fun findMoreCommon(input: List<Char>): Char {
        return if (input.count { it == '0' } > input.count { it == '1' }) '0' else '1'
    }

    fun findLeastCommon(input: List<Char>): Char {
        return if (input.count { it == '0' } > input.count { it == '1' }) '1' else '0'
    }

    fun part1(input: List<String>): Int {
        val gammaBin = StringBuilder()
        repeat(input.get(0).length) { index ->
            gammaBin.append(findMoreCommon(input.map { it[index] }))
        }
        println("$gammaBin")
        val epsilonBin = StringBuilder()
        repeat(input.get(0).length) { index ->
            epsilonBin.append(findLeastCommon(input.map { it[index] }))
        }
        println("$epsilonBin")
        val gamma = Integer.parseInt(gammaBin.toString(), 2)
        val epsilon = Integer.parseInt(epsilonBin.toString(), 2)
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        var tempOxygen = input
        var index = 0
        while (tempOxygen.size > 1) {
            val moreCommon = findMoreCommon(tempOxygen.map { it[index] })
            tempOxygen = tempOxygen.filter { it[index] == moreCommon }
            index++
        }
        var tempCO2 = input
        index = 0
        while (tempCO2.size > 1) {
            val leastCommon = findLeastCommon(tempCO2.map { it[index] })
            tempCO2 = tempCO2.filter { it[index] == leastCommon }
            index++
        }
        val oxygen = Integer.parseInt(tempOxygen[0], 2)
        val co2 = Integer.parseInt(tempCO2[0], 2)
        println("oxygen = $oxygen, co2 = $co2")

        return oxygen * co2
    }

    val input = readInput("Day03")
    println(part1(input))
    println()
    println(part2(input))
}
