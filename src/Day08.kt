fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        input.map {
            count += it.split(" ").count { it.length == (2) || it.length == (3) || it.length == 4 || it.length == 7 }
        }
        return count
    }

    fun isValid(number: Int, chars: Map<Int, Char>): Boolean {
        when (number) {
            0 -> {
                return if (chars.size == 6) {
                    !chars.contains(3)
                } else false
            }
            1 -> {
                return if (chars.size == 2) {
                    chars.contains(2) && chars.contains(5)
                } else false
            }
            2 -> {
                return if (chars.size == 5) {
                    !chars.contains(1) && !chars.contains(5)
                } else false
            }
            3 -> {
                return if (chars.size == 5) {
                    !chars.contains(1) && !chars.contains(4)
                } else false
            }
            4 -> {
                return if (chars.size == 4) {
                    !chars.contains(0) && !chars.contains(4) && !chars.contains(7)
                } else false
            }
            5 -> {
                return if (chars.size == 5) {
                    !chars.contains(2) && !chars.contains(4)
                } else false
            }
            6 -> {
                return if (chars.size == 6) {
                    !chars.contains(2)
                } else false
            }
            7 -> {
                return if (chars.size == 3) {
                    chars.contains(0) && chars.contains(2) && chars.contains(5)
                } else false
            }
            8 -> {
                return (chars.size == 7)
            }
            9 -> {
                return if (chars.size == 6) {
                    !chars.contains(4)
                } else false
            }
        }
        return false
    }

    fun getPositions(possiblePositions: Map<Char, List<Int>>) {
        var sure = mutableMapOf<Int, Char>()

        //tentativo
        var temp = mutableMapOf<Char, List<Int>>().apply { putAll(possiblePositions) }
        for (i in 1..7) {
            sure[i] = temp.filter { it.value.contains(i) }.keys.first()
            val newTemp = mutableMapOf<Char, List<Int>>()
            temp.map {
                if(it.key !in sure.values) {
                    val list =
                            if (it.value.contains(i)) {
                                it.value.filterNot { it.equals(i) }
                            } else it.value
                    newTemp.put(it.key, list)
                }
            }
            temp = newTemp

            temp.filter { it.value.contains(i) }.map {
                it.value.apply {
                    this.filterNot { it.equals(i) }
                }
            }
        }
        println(sure)
    }

    fun areAllValid(chars: Map<Int, Char>): Boolean {
        for (i in 0..9) {
            if (!isValid(i, chars))
                return false
        }
        return true
    }

    fun singleLine(input: List<String>): Map<Int, Char> {
        //be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb
        val result = mutableMapOf<Char, Int>()
        val twoChar = input.firstOrNull { it.length == 2 }
        val threeChar = input.firstOrNull { it.length == 3 }
        val fourChar = input.firstOrNull { it.length == 4 }
        val fiveChar = input.filter { it.length == 5 }
        val sixChar = input.filter { it.length == 6 }
        val sevenChar = input.firstOrNull { it.length == 7 }
        if (twoChar != null && threeChar != null) {
            val top = threeChar.toCharArray().first { !(twoChar.contains(it)) }
            result[top] = 0
        }
        val possiblePositions = mutableMapOf<Char, List<Int>>()

        //twoChar
        twoChar?.toCharArray()?.map {
            possiblePositions.put(it, listOf(2, 5))
        }

        //fourChar
        if (twoChar != null) {
            fourChar?.toCharArray()?.filter { !(twoChar.contains(it)) }?.map {
                possiblePositions.put(it, listOf(1, 3))
            }
        } else {
            fourChar?.toCharArray()?.map {
                possiblePositions.put(it, listOf(1, 2, 3, 5))
            }
        }
        (0..7).map {
            if (!possiblePositions.contains('a' + it)) {
                possiblePositions.put('a' + it, listOf(1, 2, 3, 4, 5, 6, 7))
            }
        }
        println(possiblePositions)
        getPositions(possiblePositions)
        possiblePositions.toSortedMap().map {
            if (!result.contains(it.key)) {
                result.put(it.key, it.value.first { value -> !(result.containsValue(value)) })
            }
        }
        return emptyMap()
        /* while (areAllValid(result)) {
             possiblePositions.map {

             }
         }*/
    }

    fun part2(inputs: List<String>): Int {
        singleLine(inputs[0].split(" "))
        return inputs.size
    }


    val input = readInput("Day08_2")
    val inputs = input.map { it.split("|")[0] }
    val signals = input.map { it.split("|")[1] }
    //  println(part1(signals))
    println(part2(inputs))
}
