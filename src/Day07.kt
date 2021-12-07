import kotlin.math.abs

fun main() {

    fun sommatoria(input: Int): Int {
        return input * (input + 1) / 2
    }

    fun part1(input: List<Int>): Int {
        var min = 458620
        for (i in input.indices) {
            var temp = 0
            input.map {
                temp += (abs(it - i))
            }
            if (temp < min)
                min = temp
        }
        return min
    }

    fun part2(input: List<Int>): Int {
        var min = 458699920
        for (i in input.indices) {
            var temp = 0
            input.map {
                temp += (sommatoria(abs(it - i)))
            }
            if (temp < min) {
                min = temp
            }
        }
        return min
    }

    val input = readInput("Day07")
    val ints = input.get(0).split(",").map { it.toInt() }
    println(part1(ints))
    println(part2(ints))
}
