fun main() {

    val LAST_DAY = 256

    data class LanternFish(var timer: Int = 8) {
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

    fun part1(fishes: List<LanternFish>, maxDays: Int = 80): Int {
        val map = mutableMapOf<Int, List<LanternFish>>()
        var lastDayFishes = fishes
        for (i in 0..maxDays) {
            val temp = mutableListOf<LanternFish>()
            lastDayFishes.map {
                if (it.getOld()) {
                    temp.add(it.reproduce())
                }
                temp.add(it)
            }
            map[i] = temp
            lastDayFishes = temp
        }
        return map[maxDays - 1]?.size ?: 0
    }

    fun part2(fishes: List<LanternFish>): Long {
        val map = mutableMapOf<Int, Long>()
        for (i in 0..8) {
            map[i] = fishes.count { it.timer == i }.toLong()
        }
        for (i in 0 until LAST_DAY) {
            val newFishes = map[0]
            for (days in 0..8) {
                if (days != 8)
                    map[days] = (map[days + 1] ?: 0) + (if (days == 6) newFishes ?: 0 else 0L)
                else
                    map[days] = newFishes?:0
            }
        }

        return map.values.reduce { acc, l -> acc + l }
    }

    val input = readInput("Day06")
    val fishes = input[0].split(",").map { LanternFish(timer = it.toInt()) }
    println(part1(fishes))
    println(part2(fishes))  //Day06 1687617803407
}
