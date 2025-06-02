package tool.coordinate.real

/**
 * create a Line by using the definition: Y = mX + c
 */
data class Line(val m: Double, val c: Double) {

    companion object {
        fun of(p1: Coordinate, p2: Coordinate): Line {
            val m = (p2.y - p1.y) / (p2.x - p1.x)
            return Line(
                m = m,
                c = p1.y - m * p1.x
            )
        }

        /**
         * create a Line by using the definition aX + bY = c
         *   meaning bY = -aX + c <=> Y = (-a/b)X + (c/b)
         */
        fun of(a: Double, b: Double, c: Double): Line {
            return Line(-a/b, c/b)
        }
    }

    fun intersectionPoint(other: Line): Coordinate {
        val x = (other.c - this.c) / (this.m - other.m)
        val y = this.m * x + this.c
        return Coordinate(x, y)
    }

    fun slope(): Double = m
}

