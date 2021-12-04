fun main() {
    fun part1(input: List<String>): Int {
        var temp = input[0].toInt()
        var count = 0
        input.mapIndexed { index, it ->
            if (it.toInt() > temp) count++
            temp = it.toInt()
        }
        return count
    }

    fun sumWindow(input: List<String>): Int {
        return input.map { it.toInt() }.reduce { acc, s -> acc + s }
    }

    fun part2(input: List<String>): Int {
        var temp = sumWindow(input.windowed(3)[0])
        var count = 0
        input.windowed(3).mapIndexed { index, it ->
            if (sumWindow(it) > temp) count++
            temp = sumWindow(it)
        }
        return count
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
