package tool.coordinate.twodimensional

private fun Collection<Point>.yRange(gridOrientation: Boolean): IntProgression {
    val minY = this.minByOrNull { it.y }?.y ?: -1
    val maxY = this.maxByOrNull { it.y }?.y ?: -1
    return if (gridOrientation) (minY .. maxY) else (maxY downTo minY)
}

private fun Collection<Point>.xRange(gridOrientation: Boolean): IntProgression {
    val minX = this.minByOrNull { it.x }?.x ?: -1
    val maxX = this.maxByOrNull { it.x }?.x ?: -1
    return (minX .. maxX)
}

private fun newField(gridOrientation: Boolean, x: Int, y: Int): Point =
    if (gridOrientation) pos(x,y) else xyCoordinate(x,y)

fun <T> Map<Point, T>.printAsGrid(default: String=".", itemAsString: (T)->String) {
    if (this.isEmpty())
        return
    val posType = (this.keys.first().gridOrientation)
    this.keys.yRange(posType).forEach { y ->
        this.keys.xRange(posType).forEach { x ->
            val field = newField(posType, x,y)
            if (this.contains(field)) {
                print(itemAsString(this[field]!!))
            } else {
                print(default)
            }
        }
        println()
    }
}

fun Collection<Point>.printAsGrid(itemAsString: (Point)->String) {
    if (this.isEmpty())
        return
    val gridOrientation = (this.first().gridOrientation)
    this.yRange(gridOrientation).forEach { y ->
        this.xRange(gridOrientation).forEach { x ->
            val field = newField(gridOrientation, x,y)
            print(itemAsString(field))
        }
        println()
    }
}

fun Collection<Point>.printAsGrid(defaultEmpty: String=".", defaultAvailable: String="#") {
    if (this.isEmpty())
        return
    val gridOrientation = this.first().gridOrientation
    this.yRange(gridOrientation).forEach { y ->
        this.xRange(gridOrientation).forEach { x ->
            val field = newField(gridOrientation, x,y)
            if (this.contains(field)) {
                print(defaultAvailable)
            } else {
                print(defaultEmpty)
            }
        }
        println()
    }
}

fun Pair<Point, Point>.printGrid(itemAsString: (Point)->String) {
    val gridOrientation = (this.first.gridOrientation)
    val minX = this.first.x
    val minY = this.first.y
    val maxX = this.second.x
    val maxY = this.second.y

    (minY..maxY).forEach { y ->
        (minX..maxX).forEach { x ->
            val field = newField(gridOrientation, x, y)
            print(itemAsString(field))
        }
        println()
    }
}


fun <T> List<List<T>>.printGrid(itemAsString: (gridValue: T)->String) {
    this.printGridIndexed{_, _, gridValue -> itemAsString(gridValue)}
}

fun <T> List<List<T>>.printGridIndexed(itemAsString: (row:Int, col:Int, gridValue: T)->String) {
    for (row in this.indices) {
        for (col in this[row].indices) {
            print(itemAsString(row, col, this[row][col]))
        }
        println()
    }
}