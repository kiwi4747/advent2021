fun main() {
    class Position(val x: Int, val y: Int)

    fun part1(input: List<List<Int>>): Int {
        val result = mutableListOf<Int>()
        input.mapIndexed { indexX, it ->
            it.mapIndexed { indexY, n ->
                val top = if (indexX > 0) input[indexX - 1][indexY] else 10
                val left = if (indexY > 0) input[indexX][indexY - 1] else 10
                val bottom = if (indexX < input.size - 1) input[indexX + 1][indexY] else 10
                val right = if (indexY < input[indexX].size - 1) input[indexX][indexY + 1] else 10
                if (n < top && n < left && n < bottom && n < right) {
                    result.add(n + 1)
                }
            }
        }
        return result.reduce { acc, i -> acc + i }
    }

    val input: MutableList<MutableList<Int?>>
    val inp = readInput("Day09")
    val inpList: List<List<Int?>> = inp.map { it.toCharArray().map { it.toString().toInt() } }
    input = inpList.map { it.toMutableList() }.toMutableList()
    fun macchiaDOlio(position: Position): Int {
        val indexX = position.x
        val indexY = position.y
        val top = if (indexX > 0 && (input[indexX - 1][indexY]
                        ?: 9) < 9) Position(indexX - 1, indexY) else null
        val left = if (indexY > 0 && (input[indexX][indexY - 1]
                        ?: 9) < 9) Position(indexX, indexY - 1) else null
        val bottom = if (indexX < input.size - 1 && (input[indexX + 1][indexY]
                        ?: 9) < 9) Position(indexX + 1, indexY) else null
        val right = if (indexY < input[indexX].size - 1 && (input[indexX][indexY + 1]
                        ?: 9) < 9) Position(indexX, indexY + 1) else null
        val around = mutableListOf<Position>().apply {
            top?.let {
                this.add(it)
                input[it.x][it.y] = null
            }
            bottom?.let {
                this.add(it)
                input[it.x][it.y] = null
            }
            right?.let {
                this.add(it)
                input[it.x][it.y] = null
            }
            left?.let {
                this.add(it)
                input[it.x][it.y] = null
            }
        }
        var counter = around.size
        around.forEach {
            counter += macchiaDOlio(it)
        }
        return counter
    }


    fun part2(): Int {
        val result = mutableListOf<Int>()
        input.mapIndexed { indexX, it ->
            it.mapIndexed { indexY, n ->
                if ((n ?: 9) < 9) {
                    result.add(macchiaDOlio(Position(indexX, indexY)))
                }
            }
        }
        result.sortDescending()
        println(result)
        return result[0] * result[1] * result[2]
    }

    println(part1(inp.map { it.toCharArray().map { it.toString().toInt() } }))
    println(part2())
}
