fun main() {

    fun printTable(table: List<String>) {
        println("Table")
        table.map {
            println(it)
        }
        println()
    }

    fun calculatePoints(table: List<String>): Int {
        var summona = 0
        table.map { row ->
            if (!row.contains("x x x x x"))
                summona += row.split(" ").filter { it != "x" }.filter { it.isNotBlank() }.map { it.toInt() }.reduce { acc, i -> acc + i }
        }
        return summona
    }

    fun findWinners(tables: List<List<String>>): List<List<String>> {
        val winners = mutableListOf<List<String>>()
        tables.map { table ->
            // check row
            if (table.contains("x x x x x") || table.contains(" x x x x x")) {
                winners.add(table)
            } else {
                if (table.filter { it.contains("x") }.size == 5) {
                    val tabellina = table.map { it.split(" ").filterNot { it.isEmpty() } }
                    tabellina.firstOrNull()?.let {
                        it.mapIndexed { index, s ->
                            if (s.contains("x")) {
                                if (tabellina.filter { it[index] == "x" }.size == 5) {
                                    winners.add(table)
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

    fun part1(numbersDrawn: List<String>, tables: List<List<String>>): Int {
        var temp = tables
        numbersDrawn.map { drawn ->
            temp = temp.map {
                it.map {
                    it.replace("\\b$drawn\\b".toRegex(), "x")
                }
            }
            findWinners(temp).firstOrNull()?.let {
                return calculatePoints(it) * drawn.toInt()
            }
        }
        return -1
    }

    fun part2(numbersDrawn: List<String>, tables: List<List<String>>): Int {
        var temp = tables
        numbersDrawn.map { drawn ->
            temp = temp.map {
                it.map {
                    it.replace("\\b$drawn\\b".toRegex(), "x")
                }
            }
            val winners = findWinners(temp)
            if (temp.size == 1 && winners.size == 1) {
                println("drawn = $drawn")
                return calculatePoints(temp[0]) * drawn.toInt()
            }
            temp = temp.filterNot { it in winners }
        }
        return -1
    }


    val input = readInput("Day04")

    val numbersDrawn = input[0].split(",")
    val tables = input.subList(2, input.size).map { it.replace("\\s{2,}".toRegex(), " ") }.filter { it != "" }.chunked(5)
    assert(part1(numbersDrawn, tables) == 38594)
    assert(part2(numbersDrawn, tables) == 21184)
    println(part1(numbersDrawn, tables))
    println(part2(numbersDrawn, tables))
}
