package tool.math

import kotlin.math.sqrt

// t = n(n+1)/2  ==> 0 = 0.5n^2 + 0.5n - t ==> (abc formula) ==> (-0.5 + sqrt(0.25 + 2t)) / 1 = (-1 + sqrt(1 + 8t))/2
fun Long.isTriangular(): Boolean {
    val i = ((sqrt(1.0 + 8*this) - 1) / 2)
    return (i % 1) == 0.0
}

// s = n*n ==> n = sqrt(s)
fun Long.isSquare(): Boolean {
    val i = sqrt(1.0 * this)
    return (i % 1) == 0.0
}

// p = n(3n-1)/2 ==> 0 = 1.5n^2 -0.5n - p ==> (abc formula) ==> ... (1 + sqrt(1 + 24p)) / 6
fun Long.isPentagonal(): Boolean {
    val i = ((1 + sqrt(1.0 + 24*this)) / 6)
    return (i % 1) == 0.0
}

// h = n(2n-1)  ==> 0 = 2n^2 - n - h ==> (abc formula) ==> (1 + sqrt(1 + 8h)) / 4
fun Long.isHexagonal(): Boolean {
    val i = ((1 + sqrt(1.0 + 8*this)) / 4)
    return (i % 1) == 0.0
}

// h = n(5n-3)/2  ==> 0 = (5/2)n^2 - (3/2)n - h ==> (abc formula) ==> (3 + sqrt(9 + 40h)) / 10
fun Long.isHeptagonal(): Boolean {
    val i = ((3 + sqrt(9.0 + 40*this)) / 10)
    return (i % 1) == 0.0
}

// h = n(3n-2)  ==> 0 = 3n^2 - 2n - o ==> (abc formula) ==> (2 + sqrt(4 + 12o)) / 6
fun Long.isOctagonal(): Boolean {
    val i = ((2 + sqrt(4.0 + 12*this)) / 6)
    return (i % 1) == 0.0
}

fun Long.isPolygonal(polygon: Int): Boolean {
    return when(polygon) {
        3 -> this.isTriangular()
        4 -> this.isSquare()
        5 -> this.isPentagonal()
        6 -> this.isHexagonal()
        7 -> this.isHeptagonal()
        8 -> this.isOctagonal()
        else -> throw Exception("Unknown polygonal number")
    }
}

fun Int.isTriangular() = this.toLong().isTriangular()
fun Int.isSquare() = this.toLong().isSquare()
fun Int.isPentagonal() = this.toLong().isPentagonal()
fun Int.isHexagonal() = this.toLong().isHexagonal()
fun Int.isHeptagonal() = this.toLong().isHeptagonal()
fun Int.isOctagonal() = this.toLong().isOctagonal()
fun Int.isPolygonal(polygon: Int) = this.toLong().isPolygonal(polygon)

fun getTriangle(n: Int) = n*(n+1) / 2
fun getSquare(n: Int) = n*n
fun getPentagonal(n: Int) = n*(3*n-1) / 2
fun getHexagonal(n: Int) = n * (2 * n - 1)
fun getHeptagonal(n: Int) = n * (5 * n - 3) / 2
fun getOctagonal(n: Int) = n * (3 * n - 2)

fun getPolygonal(polygon: Int, n: Int): Int {
    return when(polygon) {
        3 -> getTriangle(n)
        4 -> getSquare(n)
        5 -> getPentagonal(n)
        6 -> getHexagonal(n)
        7 -> getHeptagonal(n)
        8 -> getOctagonal(n)
        else -> throw Exception("Unknown polygonal number")
    }
}

