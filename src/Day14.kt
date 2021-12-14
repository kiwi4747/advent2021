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

    fun MutableMap<String, Long>.putOrIncrease(string: String, int: Long) {
        if (this.containsKey(string)) {
            this[string] = this[string]!! + int
        } else {
            this[string] = int
        }
    }

    fun part2(input: String, map: MutableMap<String, String>): Int {
        var start = input
        var lastChar = input.last()
        println("starting string = $start")
        val windows = start.windowed(2)
        var counterini = mutableMapOf<String, Long>()
        map.map {
            counterini.put(it.key, windows.count { window -> window == it.key }.toLong())
        }
        println("starting occurrences = $counterini")
        for (i in 0 until 40) {
            val newCounterini = mutableMapOf<String, Long>()
            counterini.map {
                val primaLettera = it.key.take(1)
                val ultimaLettera = it.key.last()
                val letteraDiMezzo = map[it.key]
                newCounterini.putOrIncrease("$primaLettera$letteraDiMezzo", it.value)
                newCounterini.putOrIncrease("$letteraDiMezzo$ultimaLettera", it.value)
            }
            counterini = newCounterini
        }

        val mapOccurrence = mutableListOf<Pair<Char, Long>>()
        for (i in 'A'..'Z') {
            var isThis = 0L
            counterini.map {
                isThis += it.key.count { it == i } * it.value
            }
            isThis /= 2
            if (i == lastChar)
                isThis++
            mapOccurrence.add(Pair(i, isThis))
        }
        val sorted = mapOccurrence.sortedBy { it.second }.filterNot { it.second == 0L }
        println("ending occurrences = $counterini")
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
        subList.put(splitted[0], splitted[1])
    }
    println(subList)
    //println(part1(string, subList))
    println(part2(string, subList))
}
