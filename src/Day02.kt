fun main() {
    fun part1(input: List<String>): Int {
        var countX = 0
        var countY = 0
        input.map {
            when {
                it.startsWith("forward") -> countX += it.split(" ")[1].toInt()
                it.startsWith("down") -> countY += it.split(" ")[1].toInt()
                it.startsWith("up") -> countY -= it.split(" ")[1].toInt()
            }
        }
        println ("countX = $countX, countY = $countY")
        return countX*countY
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var position = 0
        var depth = 0
        input.map {
            when {
                it.startsWith("forward") -> {
                    position += it.split(" ")[1].toInt()
                    depth += (aim*it.split(" ")[1].toInt())
                }
                it.startsWith("down") -> aim += it.split(" ")[1].toInt()
                it.startsWith("up") -> aim -= it.split(" ")[1].toInt()
            }
        }
        return position*depth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
