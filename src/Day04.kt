fun main() {
    fun calculatePoints(table: List<String>): Int {
        var summona = 0
        table.map { row ->
            if (row != "x x x x x")
                summona += row.split(" ").filter { it != "x" }.map { it.toInt() }.reduce { acc, i -> acc + i }
        }
        return summona

    }

    fun findWinners(tables: List<List<String>>): List<List<String>> {
        val winners = mutableListOf<List<String>>()
        tables.map { table ->
            // check row
            if (table.contains("x x x x x")) {
                winners.add(table)
            } else {
                if (table.filter { it.contains("x") }.size == 5) {
                    println(table)
                    val tabellina = table.map { it.split(" ").filterNot { it.isEmpty() } }
                    println(tabellina)
                    tabellina.map {
                        if (it.contains("x")) {
                            val index = it.indexOf("x")
                            if (tabellina.filter { it[index] == "x" }.size == 5) {
                                winners.add(table)
                            }
                        }
                    }
                } else {
                }
            }
        }
        return winners
    }

    /* fun checkTables(tables: List<List<String>>): Int {
         tables.map { table ->
             // check row
             if (table.contains("x x x x x")) {
                 return calculatePoints(table)
             } else {
                 if (table.filter { it.contains("x") }.size == 5) {
                     println(table)
                     val tabellina = table.map { it.split(" ").filter { it.isNotEmpty() } }
                     println(tabellina)
                     tabellina.map {
                         if (it.contains("x")) {
                             val index = it.indexOf("x")
                             if (tabellina.filter { it[index] == "x" }.size == 5) {
                                 return calculatePoints(table)
                             }
                         }
                     }
                 }
             }
         }
         return -1
     }
 */
    fun part1(numbersDrawn: List<String>, tables: List<List<String>>): Int {
        var temp = tables
        println(temp)
        numbersDrawn.map { drawn ->
            temp = temp.map {
                it.map {
                    it.replace("\\b$drawn\\b".toRegex(), "x")
                }
            }
            println(temp)
            findWinners(temp).firstOrNull()?.let {
                return calculatePoints(it) * drawn.toInt()
            }
        }
        return -1
    }

    fun part2(numbersDrawn: List<String>, tables: List<List<String>>): Int {
        var temp = tables
        println(temp)
        numbersDrawn.map { drawn ->
            temp = temp.map {
                it.map {
                    it.replace("\\b$drawn\\b".toRegex(), "x")
                }
            }
            val winners = findWinners(temp)
            temp = temp.filterNot { it in winners }
            println("drawn = $drawn, unwinner.size = ${temp.size}")
            println(temp.size)
            if (temp.size == 1) {
                return calculatePoints(temp[0]) * drawn.toInt()
            }
        }
        return -1
    }


    val input = readInput("Day04")

    val numbersDrawn = input[0].split(",")
    val tables = input.subList(2, input.size).map { it.replace("\\s{2,}".toRegex(), " ") }.filter { it != "" }.chunked(5)
    println(part1(numbersDrawn, tables))
    println(part2(numbersDrawn, tables))
}
