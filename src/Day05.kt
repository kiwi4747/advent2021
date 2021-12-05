import java.lang.Math.*
import kotlin.math.abs

fun main() {

    data class Position(val x: Int, val y: Int) {
        override fun toString(): String {
            return "$x,$y"
        }
    }

    data class Coordinate(val potitionStart: Position, val positionEnd: Position) {

        constructor(x1: Int, x2: Int, y1: Int, y2: Int) : this(Position(x1, y1), Position(x2, y2))

        override fun toString(): String {
            return "$potitionStart -> $positionEnd"
        }
    }


    fun fillX(map: Map<Position, Int>, coordinate: List<Coordinate>): Map<Position, Int> {
        val xMap = map.toMutableMap()
        coordinate.mapIndexed { index, item ->
            val x1 = item.potitionStart.x
            val x2 = item.positionEnd.x
            val y1 = item.potitionStart.y
            val y2 = item.positionEnd.y
            if (y1 == y2) {
                val row = mutableListOf<Position>()
                val min = min(x1, x2)
                val max = max(x1, x2)
                for (it in min..max) {
                    row.add(Position(it, y1))
                }
                row.map { coordinate ->
                    if (xMap.contains(coordinate)) {
                        val newValue = (xMap[coordinate] ?: 0).plus(1)
                        xMap[coordinate] = newValue
                    } else {
                        xMap[coordinate] = 1
                    }
                }
            }
        }
        return xMap.toMap()
    }


    fun fillY(map: Map<Position, Int>, coordinate: List<Coordinate>): Map<Position, Int> {
        val xMap = map.toMutableMap()
        coordinate.mapIndexed { index, item ->
            val x1 = item.potitionStart.x
            val x2 = item.positionEnd.x
            val y1 = item.potitionStart.y
            val y2 = item.positionEnd.y
            if (x1 == x2) {
                val column = mutableListOf<Position>()
                val min = min(y1, y2)
                val max = max(y1, y2)
                for (it in min..max) {
                    column.add(Position(x1, it))
                }
                column.map { coordinate ->
                    if (xMap.contains(coordinate)) {
                        val newValue = (xMap[coordinate] ?: 0).plus(1)
                        xMap[coordinate] = newValue
                    } else {
                        xMap[coordinate] = 1
                    }
                }
            }
        }
        return xMap.toMap()
    }

    fun fillDiagonal(map: Map<Position, Int>, coordinate: List<Coordinate>): Map<Position, Int> {
        val xMap = map.toMutableMap()
        coordinate.mapIndexed { index, item ->
            val x1 = item.potitionStart.x
            val x2 = item.positionEnd.x
            val y1 = item.potitionStart.y
            val y2 = item.positionEnd.y
            if (abs(x2 - x1) == abs(y2 - y1)) {
                val diagonal = mutableListOf<Position>()
                val min: Position
                val max: Position
                if (item.potitionStart.y > item.positionEnd.y) {
                    //diagonale dal basso verso l'alto, invertiamo
                    min = item.positionEnd
                    max = item.potitionStart
                } else {
                    min = item.potitionStart
                    max = item.positionEnd
                }
                if (min.x > max.x) {
                    //destra verso sinistra
                    val minx = min.x
                    val maxx = max.x
                    val distance =minx - maxx
                    for (move in 0..distance)
                        diagonal.add(Position(minx - move, min.y + move))
                } else {
                    //sinistra verso destra
                    val minx = min.x
                    val maxx = max.x
                    val distance = maxx - minx
                    for (move in 0..distance)
                        diagonal.add(Position(minx + move, min.y + move))
                }

                diagonal.map { coordinate ->
                    if (xMap.contains(coordinate)) {
                        val newValue = (xMap[coordinate] ?: 0).plus(1)
                        xMap[coordinate] = newValue
                    } else {
                        xMap[coordinate] = 1
                    }
                }
            }
        }
        return xMap.toMap()
    }


    fun part1(coordinates: List<Coordinate>): Int {
        var xMap = mapOf<Position, Int>()

        xMap = fillX(xMap, coordinates)
        xMap = fillY(xMap, coordinates)


        return xMap.count { entry ->
            (entry.value) > 1
        }
    }

    fun part2(coordinates: List<Coordinate>): Int {
        var xMap = mapOf<Position, Int>()
        xMap = fillX(xMap, coordinates)
        xMap = fillY(xMap, coordinates)
        xMap = fillDiagonal(xMap, coordinates)


        return xMap.count { entry ->
            (entry.value) > 1
        }
    }

    val input = readInput("Day05")
    val splitted = input.map {
        it.split(",| -> ".toRegex())
    }
    val x1s = splitted.map { it.get(0).toInt() }
    val y1s = splitted.map { it.get(1).toInt() }
    val x2s = splitted.map { it.get(2).toInt() }
    val y2s = splitted.map { it.get(3).toInt() }
    val coordinates = x1s.mapIndexed { index, item ->
        Coordinate(item, x2s.get(index), y1s.get(index), y2s.get(index))
    }
    println(coordinates)
    println(part1(coordinates))
    println(part2(coordinates))
}
