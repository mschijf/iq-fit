package tool.coordinate.twodimensional

data class LinePiece(val from: Point, val to: Point) {
    val isHorizontal = from.y == to.y
    val isVertical = from.x == to.x
    fun length() = from.distanceTo(to)
}



