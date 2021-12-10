fun main() {
    val openings = listOf<Char>('(', '[', '{', '<')
    val closings = listOf<Char>(')', ']', '}', '>')
    val points = listOf<Int>(3, 57, 1197, 25137)
    val pointsAutocomplete = listOf<Int>(1, 2, 3, 4)

    fun checkCorrupted(array: CharArray): Int {
        val opens = mutableListOf<Char>()
        array.map {
            if (it in openings) {
                opens.add(it)
            } else if (it in closings) {
                if (openings.indexOf(opens.last()) == closings.indexOf(it)) {
                    opens.removeLast()
                } else {
                    return points[closings.indexOf(it)]
                }
            } else {
                return 0
            }
        }
        return 0
    }

    fun checkIncomplete(array: CharArray): Long {
        val opens = mutableListOf<Char>()
        var points = 0L
        array.map {
            if (it in openings) {
                opens.add(it)
            } else if (it in closings) {
                if (openings.indexOf(opens.last()) == closings.indexOf(it)) {
                    opens.removeLast()
                } else {
                    println("grave error")
                    return 0
                }
            } else {
                println("gravissimo error")
                return 0
            }
        }
        while (opens.isNotEmpty()) {
            val charToAdd = closings[openings.indexOf(opens.last())]
            points *= 5
            points += pointsAutocomplete[closings.indexOf(charToAdd)]
            opens.removeLast()
        }
        return points
    }

    fun part1(input: List<String>): Int {
        var count = 0
        input.mapIndexed { index, riga ->
            val array = riga.toCharArray()
            count += checkCorrupted(array)
        }
        return count
    }

    fun part2(input: List<String>): Long {
        val points = mutableListOf<Long>()
        input.mapIndexed { index, riga ->
            val array = riga.toCharArray()
            if (checkCorrupted(array) == 0) {
                points.add(checkIncomplete(array))
            }
        }
        points.sort()
        println(points)
        val position = (points.size / 2)
        return points[position]
    }

    val input = readInput("Day10") //26397
    println(part1(input))
    println(part2(input))//2802519786
}
