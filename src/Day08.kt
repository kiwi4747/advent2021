fun main() {

    fun part1(input: List<String>): Int {
        var count = 0
        input.map {
            count += it.split(" ").count { word -> word.length == (2) || word.length == (3) || word.length == 4 || word.length == 7 }
        }
        return count
    }

    fun MutableMap<Char, List<Int>>.intersectOrPut(char: Char, list: List<Int>) {
        if (this.contains(char)) {
            this[char] = this[char]!!.intersect(list.toSet()).toList()
        } else {
            this[char] = list
        }
    }

    var lastDeleted: Pair<Int, Char>? = null

    fun getNextIntThatHasMoreThanOneChar(mappina: MutableMap<Int, List<Char>>): Int {
        val lastInt = lastDeleted?.first ?: 0
        for (i in (lastInt + 1)..mappina.size) {
            if (mappina[i]!!.size > 1) {
                return i
            }
        }
        return -1
    }

    fun getNextPositionToDelete(mappina: MutableMap<Int, List<Char>>) {
        lastDeleted = if (lastDeleted == null) {
            val nextInt = getNextIntThatHasMoreThanOneChar(mappina)
            Pair(nextInt, mappina[nextInt]!![0])
        } else {
            val lastInt = lastDeleted!!.first
            val lastChar = lastDeleted!!.second
            if (mappina[lastInt]!!.indexOf(lastChar) == (mappina[lastInt]!!.size - 1)) {
                val nextInt = getNextIntThatHasMoreThanOneChar(mappina)
                Pair(nextInt, mappina[nextInt]!![0])
            } else {
                Pair(lastInt, mappina[lastInt]!![mappina[lastInt]!!.indexOf(lastChar) + 1])
            }
        }
    }

    fun deleteNextPosition(possibleCharForPosition: MutableMap<Int, List<Char>>): MutableMap<Int, List<Char>> {
        getNextPositionToDelete(possibleCharForPosition)
        val tempMap = mutableMapOf<Int, List<Char>>()
        possibleCharForPosition.map {
            if (it.key == lastDeleted!!.first) {
                tempMap.put(it.key, it.value.filterNot { char -> char == lastDeleted!!.second })
            } else {
                tempMap.put(it.key, it.value)
            }
        }
        return tempMap
    }

    fun decipher(map: Map<Int, Char>, output: String): Int {
        val reversed = map.entries.associate { (k, v) -> v to k }
        val outputCyphered = output.split(" ")
        val wordsCyphered = mutableListOf<List<Int>>()
        outputCyphered.map { word ->
            val temp = mutableListOf<Int>()
            word.map {
                temp.add(reversed[it]!!)
            }
            temp.sort()
            if (temp.isNotEmpty()) {
                wordsCyphered.add(temp)
            }
        }
        println(wordsCyphered)
        val wordDeciphered = wordsCyphered.map {
            when (it) {
                listOf(1, 2, 3, 4, 5, 6, 7) -> 8
                listOf(1, 2, 3, 4, 6, 7) -> 9
                listOf(1, 3, 6) -> 7
                listOf(1, 2, 4, 5, 6, 7) -> 6
                listOf(1, 2, 4, 6, 7) -> 5
                listOf(2, 3, 4, 6) -> 4
                listOf(1, 3, 4, 6, 7) -> 3
                listOf(1, 3, 4, 5, 7) -> 2
                listOf(3, 6) -> 1
                listOf(1, 2, 3, 5, 6, 7) -> 0
                else -> throw java.lang.Exception()
            }
        }
        val stringBuilder = StringBuilder()
        wordDeciphered.map {
            stringBuilder.append(it)
        }
        return stringBuilder.toString().toInt()
    }

    fun getPositions(possibilitiesMap: Map<Char, List<Int>>, output: String): Int {
        val surePositions = mutableMapOf<Int, Char>()
        lastDeleted = null
        val startingMap = mutableMapOf<Int, List<Char>>()
        for (i in 1..7) {
            startingMap[i] = possibilitiesMap.filter { it.value.contains(i) }.keys.toList()
        }
        var possibleChars = startingMap
        var success = false
        while (!success) {
            success = true
            for (i in 1..7) {
                //if there is no char available for this position, delete the next possible combination and start anew
                if (possibleChars[i].isNullOrEmpty()) {
                    surePositions.clear()
                    possibleChars = deleteNextPosition(mutableMapOf<Int, List<Char>>().apply { putAll(startingMap) })
                    success = false
                    break
                } else {
                    //if there is a char available, put that in the position and delete it in all the other possible positions
                    surePositions[i] = possibleChars[i]!!.first()
                    val tempMap = mutableMapOf<Int, List<Char>>()
                    possibleChars.map {
                        if (it.key !in surePositions.keys) {
                            tempMap.put(it.key, it.value.filterNot { it.equals(surePositions[i]) })
                        }
                    }
                    possibleChars = tempMap
                }
            }
            if (success) {
                //if all the position are filled correctly, try deciphering the output. It could fail and throw an Exception
                try {
                    println(surePositions)
                    return (decipher(surePositions, output))
                } catch (e: Exception) {
                    // if it throws an exception, that means that there is at least a number bad formed.
                    // Delete next possible combination and start anew
                    success = false
                    surePositions.clear()
                    possibleChars = deleteNextPosition(mutableMapOf<Int, List<Char>>().apply {
                        putAll(startingMap)
                    })
                }
            }
        }
        return -1
    }

    fun containsAll(input: String, search: String): Boolean {
        search.toCharArray()?.map { char ->
            if (!input.toCharArray().contains(char))
                return false
        }
        return true
    }

    fun singleLine(input: List<String>, output: String): Int {
        // this is my favourite part. it checks every possible combination to reduce the possible positions. FIGHT ME.
        val thatsOne = input.firstOrNull { it.length == 2 }
        val thatsFour = input.firstOrNull { it.length == 4 }
        val thatsSeven = input.firstOrNull { it.length == 3 }
        val thatsEight = input.firstOrNull { it.length == 7 }
        val fiveChar = input.filter { it.length == 5 }
        val sixChar = input.filter { it.length == 6 }
        val thatsThree: String? =
                if (thatsSeven != null) {
                    fiveChar.firstOrNull { containsAll(it, thatsSeven) }
                } else if (thatsOne != null) {
                    fiveChar.firstOrNull { containsAll(it, thatsOne) }
                } else {
                    null
                }
        val thatsNine: String? = if (thatsFour != null) sixChar.firstOrNull { containsAll(it, thatsFour) } else null
        val possiblePositions = mutableMapOf<Char, List<Int>>()

        //if there is SEVEN
        if (thatsOne != null) {
            thatsSeven?.toCharArray()?.first { !(thatsOne.contains(it)) }?.let {
                possiblePositions.intersectOrPut(it, listOf(1))
            }
        }

        //if there is ONE
        thatsOne?.toCharArray()?.map {
            possiblePositions.intersectOrPut(it, listOf(3, 6))
        }

        //if there is FOUR
        thatsFour?.toCharArray()?.map {
            possiblePositions.intersectOrPut(it, listOf(2, 3, 4, 6))
        }
        if (thatsOne != null) {
            thatsFour?.toCharArray()?.filter { !(thatsOne.contains(it)) }?.map {
                possiblePositions.intersectOrPut(it, listOf(2, 4))
            }
        }

        //if there is THREE
        if (thatsSeven != null) {
            thatsThree?.toCharArray()?.filter { !thatsSeven.contains(it) }?.map {
                possiblePositions.intersectOrPut(it, listOf(4, 7))
            }
        }
        if (thatsOne != null) {
            thatsThree?.toCharArray()?.filter { !thatsOne.contains(it) }?.map {
                possiblePositions.intersectOrPut(it, listOf(1, 4, 7))
            }
        }

        //if there is NINE
        if (thatsFour != null) {
            thatsNine?.toCharArray()?.filter { !thatsFour.contains(it) }?.map {
                possiblePositions.intersectOrPut(it, listOf(1, 7))
            }
        }
        if (thatsThree != null) {
            thatsNine?.toCharArray()?.filter { !thatsThree.contains(it) }?.map {
                possiblePositions.intersectOrPut(it, listOf(2))
            }
        }

        //if there is EIGHT
        if (thatsFour != null) {
            thatsEight?.toCharArray()?.filter { !thatsFour.contains(it) }?.map {
                possiblePositions.intersectOrPut(it, listOf(1, 5, 7))
            }
        }
        if (thatsNine != null) {
            thatsEight?.toCharArray()?.filter { !thatsNine.contains(it) }?.map {
                possiblePositions.intersectOrPut(it, listOf(5))
            }
        }

        //fill all the position unfilled
        (0..6).map {
            possiblePositions.intersectOrPut('a' + it, listOf(1, 2, 3, 4, 5, 6, 7))
        }

        return getPositions(possiblePositions, output)
    }


    fun part2(inputs: List<String>, outputs: List<String>): Int {
        val resultsForLine = inputs.mapIndexed { index, it ->
            singleLine(it.split(" "), outputs[index])
        }
        println(resultsForLine)
        val megaSum = resultsForLine.reduce { acc, i -> acc + i }
        return megaSum
    }


    val input = readInput("Day08_2")
    val inputs = input.map { it.split("|")[0] }
    val signals = input.map { it.split("|")[1] }
    //  println(part1(signals))
    println(part2(inputs, signals))
}
