fun main() {
    val START = "start"
    val END = "end"
    fun MutableMap<String, List<String>>.addOrAdd(splitted: List<String>) {
        if (this.contains(splitted[0])) {
            val temp = this[splitted[0]]!!.toMutableList()
            temp.add(splitted[1])
            this[splitted[0]] = temp
        } else {
            this[splitted[0]] = listOf(splitted[1])
        }
    }

    fun Map<String, List<String>>.removeAllValues(value: String): Map<String, List<String>> {
        val temp = mutableMapOf<String, List<String>>()
        this.map {
            if (it.value.contains(value)) {
                temp.put(it.key, it.value.filterNot { it == value })
            } else {
                temp.put(it.key, it.value)
            }
        }
        return temp
    }

    fun Map<String, List<String>>.deleteLastCombo(value: MutableList<String>): Map<String, List<String>> {
        val temp = mutableMapOf<String, List<String>>()
        println("lastCombo = $value")
        println("this = $this")

        //nope
        return temp
    }


    var lastDeleted: Pair<String, String>? = null
    fun Map<String, List<String>>.deleteNext(): Map<String, List<String>> {
        val temp = mutableMapOf<String, List<String>>()
        var goToNext = false
        if (lastDeleted == null) {
            this.map {
                if (it.key == START) {
                    if (it.value.size > 1) {
                        val valueToDelete = it.value[0]
                        temp.put(it.key, it.value.filterNot { it == valueToDelete })
                        lastDeleted = Pair(it.key, valueToDelete)
                    } else {
                        lastDeleted = Pair(it.key, it.value[0])
                        goToNext = true
                    }
                } else {
                    temp.put(it.key, it.value)
                }
            }
        }
        if (lastDeleted != null || goToNext) {
            var found = false
            val keys = this.keys.toList()
            val index = keys.indexOfFirst { it == lastDeleted!!.first } + 1
            try {
                while (!found) {
                    val next = keys[index]
                    if (this[next]!!.size > 1) {
                    }
                }
            } catch (e: Exception) {
                println("end")
            }
        }

        return temp
    }

    fun part1(input: List<String>): Int {
        val possibleMovement = mutableMapOf<String, List<String>>()
        input.map {
            val splitted = it.split("-")
            possibleMovement.addOrAdd(splitted)
            possibleMovement.addOrAdd((listOf(splitted[1], splitted[0])))
        }
        println(possibleMovement)
        val allTentatives = mutableListOf<List<String>>()
        var startTentativePossibilities = possibleMovement.toMap()
        var end = false
        var failed = false
        val tentative = mutableListOf<String>()
        tentative.add(START)
        var lastItem = START
        for(i in 0..2) {
            while (!end || failed) {
                if (lastItem.lowercase() == lastItem) {
                    startTentativePossibilities = startTentativePossibilities.removeAllValues(lastItem)
                }
                if (startTentativePossibilities[lastItem] == null || startTentativePossibilities[lastItem]!!.isEmpty()) {
                    failed = true
                } else {
                    tentative.add(startTentativePossibilities[lastItem]!!.first())
                    lastItem = startTentativePossibilities[lastItem]!!.first()
                    if (lastItem == END)
                        end = true
                }
            }
            if (!failed) {
                println(tentative)
                allTentatives.add(tentative)
            }
            startTentativePossibilities = possibleMovement.deleteLastCombo(tentative)
        }

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day12_2")
    println(part1(input))
    println(part2(input))
}
