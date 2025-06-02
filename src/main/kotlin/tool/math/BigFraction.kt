package tool.math

import java.math.BigInteger

class BigFraction(inNumerator: BigInteger, inDenominator: BigInteger=BigInteger.ONE) : Comparable<BigFraction> {

    val numerator: BigInteger
    val denominator: BigInteger
    init {
        val gcd = inNumerator.abs().gcd(inDenominator.abs())
        if (inDenominator == BigInteger.ZERO) {
            numerator = if (inNumerator == BigInteger.ZERO)  BigInteger.ZERO else BigInteger.valueOf(inNumerator.signum().toLong())
            denominator = BigInteger.ZERO
        } else if (inNumerator == BigInteger.ZERO) {
            numerator = BigInteger.ZERO
            denominator = BigInteger.valueOf(inDenominator.signum().toLong())
        } else {
            numerator = inNumerator / gcd
            denominator = inDenominator / gcd
        }
    }

    operator fun times(other: BigFraction) =
        BigFraction(this.numerator * other.numerator,
            this.denominator * other.denominator)
    operator fun times(other: Long) =
        BigFraction(this.numerator * BigInteger.valueOf(other), this.denominator)

    operator fun div(other: BigFraction) =
        BigFraction(this.numerator * other.denominator,
            this.denominator * other.numerator)
    operator fun div(other: BigInteger) =
        BigFraction(this.numerator, this.denominator * other)

    operator fun plus(other: BigFraction) =
        BigFraction(this.numerator * other.denominator + this.denominator * other.numerator,
            this.denominator * other.denominator)
    operator fun plus(other: BigInteger) =
        BigFraction(this.numerator + this.denominator * other, this.denominator)

    operator fun minus(other: BigFraction) =
        BigFraction(this.numerator * other.denominator - this.denominator * other.numerator,
            this.denominator * other.denominator)
    operator fun minus(other: BigInteger) =
        BigFraction(this.numerator - this.denominator * other, this.denominator)

    private fun doubleValue() = numerator.toDouble() /denominator.toDouble()

    override fun compareTo(other: BigFraction): Int {
        if (this == other)
            return 0

        if (denominator == BigInteger.ZERO) {
            return if (this.numerator.signum() == other.numerator.signum())
                0
            else if (this.numerator.signum() < other.numerator.signum())
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
        return if (other is BigFraction) {
            numerator == other.numerator && denominator == other.denominator
        } else {
            super.equals(other)
        }
    }
    override fun hashCode() = Pair(numerator, denominator).hashCode()
    override fun toString() = "($numerator / $denominator)"
}
