fun main() {
    data class Row(val numbers: List<Int>)
    data class Table(val rows: List<Row>)

    val SIZE_TABLE = 5

    fun Row.isWinner(): Boolean {
        if (this.numbers.count { it == -1 } == SIZE_TABLE) {
            return true
        }
        return false
    }

    fun Table.hasWinningRow(): Boolean {
        if (this.rows.any { it.isWinner() }) {
            return true
        }
        return false
    }

    fun Table.hasWinningColumn(): Boolean {
        if (this.rows.count { it.numbers.contains(-1) } == SIZE_TABLE) {
            this.rows[0].numbers.mapIndexed { index, s ->
                if (s == -1) {
                    if (this.rows.count { it.numbers[index] == -1 } == SIZE_TABLE) {
                        return true
                    }
                }
            }
        }
        return false
    }


    fun Row.findNumber(drawn: Int): Row {
        return Row(this.numbers.map { if (it == drawn) -1 else it })
    }

    fun Table.findNumber(drawn: Int): Table {
        return Table(this.rows.map { it.findNumber(drawn) })
    }

    fun Table.calculatePoints(): Int {
        var total = 0
        this.rows.map { row ->
            if (!row.isWinner())
                total += row.numbers.filterNot { it == -1 }.reduce { acc, i -> acc + i }
        }
        return total
    }


    fun findWinners(tables: List<Table>): List<Table> {
        val winners = mutableListOf<Table>()
        tables.map { table ->
            if (table.hasWinningRow() || table.hasWinningColumn()) {
                winners.add(table)
            }
        }
        return winners
    }

    fun part1(numbersDrawn: List<Int>, tables: List<Table>): Int {
        var temp = tables
        numbersDrawn.map { drawn ->
            temp = temp.map { table -> table.findNumber(drawn) }

            findWinners(temp).firstOrNull()?.let {
                return it.calculatePoints() * drawn
            }
        }
        return -1
    }

    fun part2(numbersDrawn: List<Int>, tables: List<Table>): Int {
        var temp = tables
        numbersDrawn.map { drawn ->
            temp = temp.map { table ->
                table.findNumber(drawn)
            }
            val winners = findWinners(temp)
            if (temp.size == 1 && winners.size == 1) {
                return temp[0].calculatePoints() * drawn
            }
            temp = temp.filterNot { it in winners }
        }
        return -1
    }


    val input = readInput("Day04")

    val numbersDrawn = input[0].split(",").map { it.toInt() }
    val temptables = input.subList(2, input.size)
            .filter { it.isNotBlank() }
            .chunked(5)
            .map {
                it.map {
                    it.split(" ")
                            .filter { it.isNotBlank() }.map { it.toInt() }
                }
            }
    val tabb = temptables.map { table -> Table(table.map { row -> Row(row) }) }

    println(part1(numbersDrawn, tabb)) // 38594
    println(part2(numbersDrawn, tabb)) // 21184
}
