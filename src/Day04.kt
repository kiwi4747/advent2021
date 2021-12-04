fun main() {

    fun printTable(table: List<String>) {
        println("Table")
        table.map {
            println(it)
        }
        println()
    }

    fun isTableWinnerByRow(table: List<List<Int>>): Boolean {
        table.map { row ->
            if (row.count { it == -1 } == 5) {
                return true
            }
        }
        return false
    }

    fun isRowWinner(row: List<Int>): Boolean {
        if (row.count { it == -1 } == 5) {
            return true
        }
        return false
    }

    fun calculatePoints(table: List<List<Int>>): Int {
        var summona = 0
        table.map { row ->
            if (!isRowWinner(row))
                summona += row.filterNot { it == -1 }.reduce { acc, i -> acc + i }
        }
        return summona
    }


    fun findWinners(tables: List<List<List<Int>>>): List<List<List<Int>>> {
        val winners = mutableListOf<List<List<Int>>>()
        tables.map { table ->
            // check row
            if (isTableWinnerByRow(table)) {
                winners.add(table)
            } else {
                if (table.filter { it.contains(-1) }.size == 5) {
                    table.first().let {
                        it.mapIndexed { index, s ->
                            if (s == -1) {
                                if (table.filter { it[index] == -1 }.size == 5) {
                                    winners.add(table)
                                    return@let
                                }
                            }
                        }
                    }
                } else {
                }
            }
        }
        return winners
    }

    fun part1(numbersDrawn: List<Int>, tables: List<List<List<Int>>>): Int {
        var temp = tables
        numbersDrawn.map { drawn ->
            temp = temp.map {
                it.map {
                    it.map {
                        if (it == drawn) -1
                        else it
                    }
                }
            }
            findWinners(temp).firstOrNull()?.let {
                return calculatePoints(it) * drawn.toInt()
            }
        }
        return -1
    }

    fun part2(numbersDrawn: List<Int>, tables: List<List<List<Int>>>): Int {
        var temp = tables
        numbersDrawn.map { drawn ->
            temp = temp.map {
                it.map {
                    it.map {
                        if (it == drawn) -1
                        else it
                    }
                }
            }
            val winners = findWinners(temp)
            if (temp.size == 1 && winners.size == 1) {
                println("drawn = $drawn")
                return calculatePoints(temp[0]) * drawn
            }
            temp = temp.filterNot { it in winners }
        }
        return -1
    }


    val input = readInput("Day04")

    val numbersDrawn = input[0].split(",").map { it.toInt() }
    val tables = input.subList(2, input.size).map { it.replace("\\s{2,}".toRegex(), " ") }.filter { it != "" }
            .chunked(5)
            .map {
                it.map {
                    it.split(" ")
                            .filter { it.isNotBlank() }.map { it.toInt() }
                }
            }

    // assert(part1(numbersDrawn, tables) == 38594)
    //assert(part2(numbersDrawn, tables) == 21184)
    println(part1(numbersDrawn, tables))
    println(part2(numbersDrawn, tables))
}
