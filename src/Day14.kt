fun main() {
    fun String.insert(index: Int, string: String): String {
        return this.substring(0, index) + string + this.substring(index, this.length)
    }

    fun part1(input: String, map: MutableMap<String, String>): Int {
        var start = input
        println(start)
        for (i in 0 until 5) {
            var temp = start.windowed(2)
            println("iteration $i")
            println(temp)
            var stringa = StringBuilder()
            temp.map {
                if (map.containsKey(it)) {
                    stringa.append(it.take(1))
                    stringa.append(map[it])
                }
            }
            stringa.append(temp.last().last())
            start = stringa.toString()
            println(start)
        }
        val mapOccurrence = mutableListOf<Pair<Char, Int>>()
        for (i in 'A'..'Z') {
            mapOccurrence.add(Pair(i, start.count { it == i }))
        }
        val sorted = mapOccurrence.sortedBy { it.second }.filterNot { it.second == 0 }
        val countMost = sorted.last().second
        val countLeast = sorted.first().second
        println("countMost= $countMost, countLeast =$countLeast, difference = ${countMost - countLeast}")
        return start.length
    }


    val input = readInput("Day14")
    val string = input[0]
    val subList = mutableMapOf<String, String>()
    input.subList(2, input.size).map {
        val splitted = it.split(" -> ")
        //subList.put(splitted[0], splitted[0].insert(1, splitted[1]))
        subList.put(splitted[0], splitted[1])
    }
    println(subList)
    println(part1(string, subList))
}
