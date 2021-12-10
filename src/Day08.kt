fun main() {

    fun part1(input: List<String>): Int {
        var count = 0
        input.map {
            count += it.split(" ").count { it.length == (2) || it.length == (3) || it.length == 4 || it.length == 7 }
        }
        return count
    }

    fun MutableMap<Char, List<Int>>.intersectOrDont(char: Char, list: List<Int>) {
        if (this.contains(char)) {
            this[char] = this[char]!!.intersect(list.toSet()).toList()
        } else {
            this[char] = list
        }
    }

    var lastDeleted: Pair<Int, Char>? = null

    fun deleteNextPosition(mappina: MutableMap<Int, List<Char>>): MutableMap<Int, List<Char>> {
        if (lastDeleted == null) {
            lastDeleted = Pair(1, mappina[1]!![0])
        } else {
            val lastInt = lastDeleted!!.first
            val lastChar = lastDeleted!!.second
            if (mappina[lastInt]!!.indexOf(lastChar) == (mappina[lastInt]!!.size - 1)) {
                lastDeleted = Pair(lastInt + 1, mappina[lastInt + 1]!!.get(0))
            } else {
                lastDeleted = Pair(lastInt, mappina[lastInt]!!.get(mappina[lastInt]!!.indexOf(lastChar) + 1))
            }
        }
        val mappinaDeleted = mutableMapOf<Int, List<Char>>()
        mappina.map {
            if (it.key == lastDeleted!!.first) {
                mappinaDeleted.put(it.key, it.value.filterNot { it == lastDeleted!!.second })
            } else {
                mappinaDeleted.put(it.key, it.value)
            }
        }
        return mappinaDeleted
    }

    fun decypher(map: Map<Int, Char>, output: String): Int {
        val reversed = map.entries.associate { (k, v) -> v to k }
        val list = output.split(" ")
        val results = mutableListOf<List<Int>>()
        list.map { word ->
            var resultino = mutableListOf<Int>()
            word.map {
                resultino.add(reversed[it]!!)
            }
            resultino.sort()
            if (resultino.isNotEmpty()) {
                results.add(resultino)
            }
        }
        val risultatoDavvero = results.map {
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
        risultatoDavvero.map {
            stringBuilder.append(it)
        }
        println(risultatoDavvero)
        println(" ${stringBuilder.toString().toInt()}")
        return stringBuilder.toString().toInt()
    }

    fun getPositions(possiblePositions: Map<Char, List<Int>>, output: String): Map<Int, Char> {
        var sure = mutableMapOf<Int, Char>()
        lastDeleted = null
        val startTempInvertito = mutableMapOf<Int, List<Char>>()
        for (i in 1..7) {
            startTempInvertito.put(i, possiblePositions.filter { it.value.contains(i) }.keys.toList())
        }
        //tentativo
        var tempInvertito = startTempInvertito
        println(tempInvertito)
        var failed = true
        while (failed) {
            failed = false
            for (i in 1..7) {
                if (tempInvertito[i].isNullOrEmpty()) {
                    sure.clear()
                    //   println("${tempInvertito[i]} is null or empty")
                    tempInvertito = deleteNextPosition(mutableMapOf<Int, List<Char>>().apply { putAll(startTempInvertito) })
                    //   println(tempInvertito)
                    failed = true
                    break
                } else {
                    sure[i] = tempInvertito[i]!!.first()
                    //   println("sure=$sure")
                    val newTemp = mutableMapOf<Int, List<Char>>()
                    tempInvertito.map {
                        if (it.key !in sure.keys) {
                            newTemp.put(it.key, it.value.filterNot { it.equals(sure[i]) })
                        }
                    }
                    tempInvertito = newTemp
                    //    println(tempInvertito)
                }
            }
            if (!failed)
                try {
                    //   println(sure)
                    decypher(sure, output)
                } catch (e: Exception) {
                    failed = true
                    tempInvertito = deleteNextPosition(mutableMapOf<Int, List<Char>>().apply {
                        putAll(startTempInvertito)
                    })
                }
        }
        //  println(sure)
        return sure
    }

    fun containsAll(input: String, search: String): Boolean {
        search.toCharArray()?.map { char ->
            if (!input.toCharArray().contains(char))
                return false
        }
        return true
    }

    fun singleLine(input: List<String>, output: String): Int {
        //be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb
        val thatsOne = input.firstOrNull { it.length == 2 }
        val thatsSeven = input.firstOrNull { it.length == 3 }
        val thatsEight = input.firstOrNull { it.length == 7 }
        val thatsFour = input.firstOrNull { it.length == 4 }
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

        if (thatsOne != null && thatsSeven != null) {
            val top = thatsSeven.toCharArray().first { !(thatsOne.contains(it)) }
            possiblePositions.intersectOrDont(top, listOf(1))
        }

        //twoChar
        thatsOne?.toCharArray()?.map {
            possiblePositions.intersectOrDont(it, listOf(3, 6))
        }

        //fourChar
        thatsFour?.toCharArray()?.map {
            possiblePositions.intersectOrDont(it, listOf(2, 3, 4, 6))
        }
        if (thatsOne != null) {
            thatsFour?.toCharArray()?.filter { !(thatsOne.contains(it)) }?.map {
                possiblePositions.intersectOrDont(it, listOf(2, 4))
            }
        }

        //bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef

        //three
        if (thatsSeven != null) {
            thatsThree?.toCharArray()?.filter { !thatsSeven.contains(it) }?.map {
                possiblePositions.intersectOrDont(it, listOf(4, 7))
            }
        }
        if (thatsOne != null) {
            thatsThree?.toCharArray()?.filter { !thatsOne.contains(it) }?.map {
                possiblePositions.intersectOrDont(it, listOf(1, 4, 7))
            }
        }

        //nine
        if (thatsFour != null) {
            thatsNine?.toCharArray()?.filter { !thatsFour.contains(it) }?.map {
                possiblePositions.intersectOrDont(it, listOf(1, 7))
            }
        }
        if (thatsThree != null) {
            thatsNine?.toCharArray()?.filter { !thatsThree.contains(it) }?.map {
                possiblePositions.intersectOrDont(it, listOf(2))
            }
        }

        //eight
        if (thatsFour != null) {
            thatsEight?.toCharArray()?.filter { !thatsFour.contains(it) }?.map {
                possiblePositions.intersectOrDont(it, listOf(1, 5, 7))
            }
        }
        if (thatsNine != null) {
            thatsEight?.toCharArray()?.filter { !thatsNine.contains(it) }?.map {
                possiblePositions.intersectOrDont(it, listOf(5))
            }
        }

        (0..6).map {
            possiblePositions.intersectOrDont('a' + it, listOf(1, 2, 3, 4, 5, 6, 7))
        }

        println(possiblePositions)

        return decypher(getPositions(possiblePositions, output), output)
    }


    fun part2(inputs: List<String>, outputs: List<String>): Int {
        val risultini = inputs.mapIndexed { index, it ->
            singleLine(it.split(" "), outputs[index])
        }
        println(risultini)
        val megaSum = risultini.reduce { acc, i -> acc + i }
        return megaSum
    }


    val input = readInput("Day08")
    val inputs = input.map { it.split("|")[0] }
    val signals = input.map { it.split("|")[1] }
    //  println(part1(signals))
    println(part2(inputs, signals))
}
