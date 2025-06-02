package tool.math

import com.tool.math.gcd
import kotlin.math.absoluteValue
import kotlin.math.sign

class Fraction(inNumerator: Long, inDenominator: Long=1) : Comparable<Fraction> {

    constructor(inNumerator: Int, inDenominator: Int=1):this(inNumerator.toLong(), inDenominator.toLong())

    val numerator: Long
    val denominator: Long
    init {
        val gcd = gcd(inNumerator.absoluteValue, inDenominator.absoluteValue)
        if (inDenominator == 0L) {
            numerator = if (inNumerator == 0L) 0 else inNumerator.sign.toLong()
            denominator = 0
        } else if (inNumerator == 0L) {
            numerator = 0
            denominator = inDenominator.sign.toLong()
        } else {
            numerator = inNumerator / gcd
            denominator = inDenominator / gcd
        }
    }

    operator fun times(other: Fraction) =
        Fraction(this.numerator * other.numerator,
            this.denominator * other.denominator)
    operator fun times(other: Long) =
        Fraction(this.numerator * other, this.denominator)

    operator fun div(other: Fraction) =
        Fraction(this.numerator * other.denominator,
            this.denominator * other.numerator)
    operator fun div(other: Long) =
        Fraction(this.numerator, this.denominator * other)

    operator fun plus(other: Fraction) =
        Fraction(this.numerator * other.denominator + this.denominator * other.numerator,
            this.denominator * other.denominator)
    operator fun plus(other: Long) =
        Fraction(this.numerator + this.denominator * other, this.denominator)

    operator fun minus(other: Fraction) =
        Fraction(this.numerator * other.denominator - this.denominator * other.numerator,
            this.denominator * other.denominator)
    operator fun minus(other: Long) =
        Fraction(this.numerator - this.denominator * other, this.denominator)

    private fun doubleValue() = numerator.toDouble() /denominator.toDouble()

    override fun compareTo(other: Fraction): Int {
        if (this == other)
            return 0

        if (denominator == 0L) {
            return if (this.numerator.sign == other.numerator.sign)
                0
            else if (this.numerator.sign < other.numerator.sign)
                -1
            else
                1
        } else {
            return if (this.doubleValue() < other.doubleValue())
                -1
            else
                1
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Fraction) {
            numerator == other.numerator && denominator == other.denominator
        } else {
            super.equals(other)
        }
    }
    override fun hashCode() = Pair(numerator, denominator).hashCode()
    override fun toString() = "($numerator / $denominator)"

    fun asDecimalString(): String {
        var decimalFraction = ""
        val remainderList = mutableListOf<Long>()

        var remainder = (numerator % denominator) * 10
        remainderList.add(remainder)
        while (remainder != 0L && remainder < denominator) {
            remainder *= 10
            remainderList.add(remainder)
            decimalFraction += "0"
        }

        while (remainder != 0L) {
            decimalFraction += remainder / denominator

            remainder = (remainder % denominator) * 10
            if (remainder == 0L || remainder in remainderList)
                break
            remainderList.add(remainder)
            while (remainder < denominator) {
                remainder *= 10
                remainderList.add(remainder)
                decimalFraction += "0"
            }
        }

        val cycleLength = if (remainder != 0L && remainder in remainderList) remainderList.size - remainderList.indexOf(remainder) else 0
        val fixedFractionPart = decimalFraction.take(decimalFraction.length - cycleLength)
        val reciprocalCycle = if (cycleLength > 0) "(" + decimalFraction.takeLast(cycleLength) + ")" else ""
        val beforeComma = numerator / denominator
        val comma = if (decimalFraction.isNotEmpty()) "." else ""
        return "$beforeComma$comma$fixedFractionPart$reciprocalCycle"
    }


    fun reciprocalCycleLength(): Int {
        var remainder = (numerator % denominator) * 10
        if (remainder == 0L) {
            return 0
        }

        val remainderList = mutableListOf<Long>()
        remainderList.add(remainder)
        while (remainder != 0L && remainder < denominator) {
            remainder *= 10
            remainderList.add(remainder)
        }

        while (remainder != 0L) {
            remainder = (remainder % denominator) * 10
            if (remainder == 0L || remainder in remainderList)
                break
            remainderList.add(remainder)
            while (remainder < denominator) {
                remainder *= 10
                remainderList.add(remainder)
            }
        }

        return if (remainder != 0L && remainder in remainderList)
            remainderList.size - remainderList.indexOf(remainder)
        else
            0
    }
}
