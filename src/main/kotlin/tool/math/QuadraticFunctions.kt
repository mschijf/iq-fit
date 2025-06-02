package tool.math

import kotlin.math.sqrt

// for: ax^2+bx+c
// the 'abc formula is:
//
//  -b +/- SQRT(b^2 - 4ac)
//  -----------------------
//           2a
//
fun quadraticFormulaSolutions(a: Double, b: Double, c: Double): List<Double> {
    val discriminant = b * b - 4 * a * c
    return when (discriminant.compareTo(0.0)) {
        -1 -> emptyList()
        0 -> listOf(-b / 2 * a)
        1 -> listOf((-b + sqrt( discriminant)) / (2 * a), (-b - sqrt(discriminant)) / (2 * a))
        else -> throw Exception("Impossible")
    }
}

