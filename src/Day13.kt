fun main() {
    data class Position(val x: Int, val y: Int) {
        override fun toString(): String {
            return "$x,$y"
        }

    }


    data class Fold(val direction: Int, val value: Int) //x==1, y==2


    fun part2(input: List<Position>): Int {
        val m = input.sortedBy { it.y }
        println(m)
        var lastY = -1
        var lastX = 0
        var row = StringBuilder()
        m.map {
            if (lastY < it.y) {
                println(row.toString())
                lastY = it.y
                lastX = 0
                row = StringBuilder()
            }
            for (i in 1 until (it.x - lastX)) {
                row.append(".")
            }
            row.append("X")
            lastX = it.x
        }
        println(row.toString())
        return input.size
    }

    fun part1(input: List<Position>, folds: List<Fold>): List<Position> {
        var startList = input
        println(startList)
        println(startList.size)
        //    part2(startList)
        println("--------")

        folds.map { fold ->
            var countOverriden = 0
            println("fold ${fold.direction} ${fold.value}")
            val result = mutableListOf<Position>()
            if (fold.direction == 1) {
                var numberOfRows = (fold.value * 2)//0 //may be fold *2
                startList.map { position ->
                    if (position.x < fold.value) {
                        result.add(position)
                    } else if (position.x == fold.value) {
                        //shit
                    } else {
                        if (result.contains(Position(numberOfRows - position.x, position.y))) {
                      //      println("this was overriden, ${Position(numberOfRows - position.x, position.y)} to $position")
                            countOverriden++
                        } else {
                            result.add(Position(numberOfRows - position.x, position.y))
                        }
                    }
                }
            } else {
                var numberOfColumn = (fold.value * 2)
                startList.map { position ->
                    if (position.y < fold.value) {
                        result.add(position)
                    } else if (position.y == fold.value) {
                        //shit
                    } else {
                        if (result.contains(Position(position.x, numberOfColumn - position.y))) {
                   //         println("this was overriden, ${Position(position.x, numberOfColumn - position.y)} to $position")
                            countOverriden++
                        } else {
                            result.add(Position(position.x, numberOfColumn - position.y))
                        }
                    }
                }
            }
            startList = result.sortedBy { it.y }.sortedBy { it.x }
            println(startList)
            println(startList.size)
            println("countOverriden = $countOverriden")
            //  println(part2(startList))
            println("--------")
        }
        return (startList)
    }

    val input = readInput("Day13")
    // fold along y=7
    // fold along x=5
    val mappa = mutableListOf<Position>()
    val folds = mutableListOf<Fold>()
    input.map {
        var splitted = it.split(",")
        if (splitted.size == 2) {
            mappa.add(Position(splitted[0].toInt(), splitted[1].toInt()))
        } else {
            splitted = it.split("=")
            if (splitted.size == 2) {
                val direction =
                        if (splitted[0].endsWith("y")) {
                            2
                        } else 1
                val value = splitted[1].toInt()
                folds.add(Fold(direction, value))
            } else {
            }
        }
    }
    println(part2(part1(mappa, folds)))
}
