import javax.swing.Popup
import javax.swing.text.Position

fun main() {
    data class Position(val x: Int, val y: Int)
    fun Map<Position, Int>.print(): String {
        var count = 1
        val string = StringBuilder()
        this.map {
            string.append(it.value)
            if (count % 10 == 0) {
                string.append("\n")
            }
            count++
        }
        return string.toString()
    }


    fun Map<Position, Int>.resetTo0Flashed(): Map<Position, Int> {
        val temp = mutableMapOf<Position, Int>()
        this.map {
            if (it.value > 9) {
                temp.put(it.key, 0)
            } else {
                temp.put(it.key, it.value)
            }
        }
        return temp
    }

    fun MutableMap<Position, Int>.incrementEnergyInPosition(position: Position, startingMap: Map<Position, Int>) {
        this[position] = startingMap[position]!! + 1
    }

    fun MutableMap<Position, Int>.incrementAllAround(position: Position, startingMap: Map<Position, Int>) {
        val x = position.x
        val y = position.y
        if (x > 0) {
            val positionLeft = Position(x - 1, y)
            this.incrementEnergyInPosition(positionLeft, startingMap)
            if (y > 0) {
                val positionLeftTop = Position(x - 1, y - 1)
                this.incrementEnergyInPosition(positionLeftTop, startingMap)
            }
            if (y < 9) {
                val positionLeftBottom = Position(x - 1, y + 1)
                this.incrementEnergyInPosition(positionLeftBottom, startingMap)
            }
        }
        if (x < 9) {
            val positionRight = Position(x + 1, y)
            this.incrementEnergyInPosition(positionRight, startingMap)
            if (y > 0) {
                val positionRightTop = Position(x + 1, y - 1)
                this.incrementEnergyInPosition(positionRightTop, startingMap)
            }
            if (y < 9) {
                val positionRightBottom = Position(x + 1, y + 1)
                this.incrementEnergyInPosition(positionRightBottom, startingMap)
            }
        }
        if (y > 0) {
            val positionTop = Position(x, y - 1)
            this.incrementEnergyInPosition(positionTop, startingMap)

        }
        if (y < 9) {
            val positionBottom = Position(x, y + 1)
            this.incrementEnergyInPosition(positionBottom, startingMap)
        }
    }

    fun part1(input: Map<Position, Int>): Int {
        var countFlashes = 0
        println(input.toMap().print())
        //step increment by 1
        var incremented = mutableMapOf<Position, Int>()
        input.map { incremented.put(Position(it.key.x, it.key.y), it.value + 1) }
        println("increment all by one")
        println(incremented.toMap().print())
        for (i in 1..100) {
            //step every octopus ready, flashes and diffuses the energy
            val hasAlreadyFlashedInThisStep = mutableListOf<Position>()
            while (incremented.any { it.value > 9 && it.key !in hasAlreadyFlashedInThisStep }) {
                var temp = incremented
                incremented.map {
                    if (it.value > 9 && it.key !in hasAlreadyFlashedInThisStep) {
                        countFlashes++
                        hasAlreadyFlashedInThisStep.add(it.key)
                        temp.incrementAllAround(it.key, incremented)
                        println("increment all around pos ${it.key.x}, ${it.key.y}")
                        println(incremented.toMap().print())
                    }
                }
                incremented = temp
            }

            //put all values>8 to 0
            incremented = incremented.resetTo0Flashed().toMutableMap()
            val temp = mutableMapOf<Position, Int>()
            incremented.map { temp.put(Position(it.key.x, it.key.y), it.value + 1) }

            println(incremented.toMap().print())
            incremented = temp
        }
        //step1
        return countFlashes
    }

    fun part2(input: Map<Position, Int>): Int {
        var countFlashes = 0
        println(input.toMap().print())
        //step increment by 1
        var incremented = mutableMapOf<Position, Int>()
        input.map { incremented.put(Position(it.key.x, it.key.y), it.value + 1) }
        for (i in 1..400) {
            //step every octopus ready, flashes and diffuses the energy
            val hasAlreadyFlashedInThisStep = mutableListOf<Position>()
            while (incremented.any { it.value > 9 && it.key !in hasAlreadyFlashedInThisStep }) {
                var temp = incremented
                incremented.map {
                    if (it.value > 9 && it.key !in hasAlreadyFlashedInThisStep) {
                        countFlashes++
                        hasAlreadyFlashedInThisStep.add(it.key)
                        temp.incrementAllAround(it.key, incremented)
                    }
                }
                incremented = temp
            }

            //put all values>8 to 0
            incremented = incremented.resetTo0Flashed().toMutableMap()
            println(incremented.toMap().print())
            val temp = mutableMapOf<Position, Int>()
            incremented.map { temp.put(Position(it.key.x, it.key.y), it.value + 1) }

            incremented = temp
            println("step $i")
            if(hasAlreadyFlashedInThisStep.size==100){
                return i
            }
        }
        //step1
        return -1
    }

    val input = readInput("Day11")
    val map = mutableMapOf<Position, Int>()

    input.mapIndexed { indexY, row -> row.toCharArray().mapIndexed { indexX, it -> map.put(Position(indexX, indexY), it.toString().toInt()) } }

   // println(part1(map.toMap()))
    println(part2(map.toMap()))
}
