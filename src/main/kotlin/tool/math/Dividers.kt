package tool.math

import kotlin.math.sqrt

/**
 * A proper divisor of a number is any divisor of that number other than the number itself.
 * For example, consider the number 12. The divisors of 12 are as follows:
 * Divisors of 12: 1, 2, 3, 4, 6, and 12.
 */
fun Int.properDivisors(): Set<Int> {
    val resultSet = mutableSetOf(1)
    for (i in 2 ..sqrt(this.toDouble()).toInt()) {
        if (this % i == 0) {
            resultSet.add(i)
            resultSet.add(this / i)
        }
    }
    return resultSet
}

/**
 * A proper divisor of a number is any divisor of that number other than the number itself.
 * For example, consider the number 12. The divisors of 12 are as follows:
 * Divisors of 12: 1, 2, 3, 4, 6, and 12.
 */
fun Int.dividerCount(): Int {
    var count = 1
    for (i in 2 ..sqrt(this.toDouble()).toInt()) {
        if (this % i == 0)
            count++
    }
    return 2*count
//    return properDivisors().count()+1
}

/**
 * A proper divisor of a number is any divisor of that number other than the number itself.
 * For example, consider the number 12. The divisors of 12 are as follows:
 * Divisors of 12: 1, 2, 3, 4, 6, and 12.
 */
fun Int.sumOfProperDivisors(): Int {
    var sum = 1
    for (i in 2 .. sqrt(this.toDouble()).toInt()) {
        if (this % i == 0) {
            val other = this/i
            sum += (i + if (i != other) other  else 0)
        }
    }
    return sum
//    return properDivisors().sum()
}

fun Int.primeDividers(): Set<Int> {
    return this.properDivisors().filter{it.isPrime()}.toSet()
}
