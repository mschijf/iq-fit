package tool.primarytype

/**
 * returns a list of the digits. The lowest digit will be put at index 0.
 *
 * Example: 3456.toDigitList() = [[3,4,5,6]]
 */
fun Int.toDigitList(): List<Int> = this.toLong().toDigitList()

/**
 * returns a list of the digits. The lowest digit will be put at index 0.
 *
 * Example: 3456.toDigitList() = [[3,4,5,6]]
 */
fun Long.toDigitList(): List<Int> {
    val result = mutableListOf<Int>()
    var base = this
    while (base != 0L) {
        result.add( (base % 10).toInt())
        base /= 10
    }
    return result
}

fun Int.toDigitSet() = this.toLong().toDigitSet()
fun Long.toDigitSet(): Set<Int> {
    val result = mutableSetOf<Int>()
    var base = this
    while (base != 0L) {
        result.add( (base % 10).toInt())
        base /= 10
    }
    return result
}

fun Int.pow(power: Int):Long {
    var base = 1L
    repeat(power) {
        base *= this
    }
    return base
}

fun Int.log10(): Int = this.toLong().log10()
fun Long.log10(): Int {
    var base = this
    var c = -1
    while (base > 0L) {
        base /= 10
        c++
    }
    return c
}


fun Int.digitLength() = this.toLong().digitLength().toInt()
fun Long.digitLength() = this.log10()+1

fun Int.digitSum() = this.toLong().digitSum().toInt()
fun Long.digitSum(): Long {
    var sum = 0L
    var base = this
    while (base != 0L) {
        sum += base % 10
        base /= 10
    }
    return sum
}

