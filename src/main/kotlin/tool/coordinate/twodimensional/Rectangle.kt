package tool.coordinate.twodimensional

import tool.primarytype.overlaps
import kotlin.math.max
import kotlin.math.min


data class Rectangle(val p1: Point, val p2: Point) {
    val minX = min(p1.x, p2.x)
    val maxX = max(p1.x, p2.x)
    val minY = min(p1.y, p2.y)
    val maxY = max(p1.y, p2.y)

    fun overlaps(other: Rectangle): Boolean {
        val i1 = (minX..maxX).overlaps(other.minX..other.maxX)
        val i2 = (minY..maxY).overlaps(other.minY..other.maxY)
        return i1 && i2
    }

    fun topLeft(): Point = pos(minX, minY)
    fun topRight(): Point = pos(maxX, minY)
    fun bottomLeft(): Point = pos(minX, maxY)
    fun bottomRight(): Point = pos(maxX, maxY)

    fun area() : Long {
        return LinePiece(topLeft(), topRight()).length().toLong() * LinePiece(topRight(), bottomRight()).length()
    }

}

