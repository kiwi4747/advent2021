fun main() {

    data class LanternFish(var timer: Int) {
        override fun toString(): String {
            return "$timer"
        }

        fun getOld(): Boolean {
            val reproduce = timer == 0
            if (reproduce) {
                timer = 6
            } else {
                timer--
            }
            return reproduce
        }

        fun reproduce(): LanternFish {
            return LanternFish(8)
        }
    }
    fun part1(fishes: List<LanternFish>, maxDays : Int = 256): Int {
        val map = mutableMapOf<Int, List<LanternFish>>()
        var lastDayFishes = fishes
        for(i in 0 .. maxDays) {
            val temp = mutableListOf<LanternFish>()
            lastDayFishes.map {
                if (it.getOld()) {
                    temp.add(it.reproduce())
                }
                temp.add(it)
            }
            map.put(i, temp)
            lastDayFishes = temp
        }
        return map.toMap().get(maxDays-1)?.size?:0
    }

    fun part2(fishes: List<LanternFish>): Int {
        return fishes.size
    }

    val input = readInput("Day06")
    val fishes = input[0].split(",").map { LanternFish(it.toInt()) }
    println(part1(fishes))
    println(part2(fishes))
}
