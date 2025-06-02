package com.tool.math

fun gcd(a: Int, b: Int): Int {
    return gcd(a.toLong(), b.toLong()).toInt()
}

fun lcm(a: Int, b: Int): Int {
    return lcm(a.toLong(), b.toLong()).toInt()
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) {
        a
    } else
        gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return a*b / gcd(a,b)
}

