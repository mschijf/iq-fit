package tool.coordinate.twodimensional

import kotlin.math.absoluteValue

fun pos(x: Int, y: Int) = Point.of(gridOrientation = true, x,y)
fun pos(raw:String) = Point.of(gridOrientation = true, raw)

fun xyCoordinate(x: Int, y: Int) = Point.of(gridOrientation = false, x,y)
fun xyCoordinate(raw:String) = Point.of(gridOrientation = false, raw)

fun posRange(minPos: Point, maxPos: Point) = sequence<Point> {
    (minPos.x .. maxPos.x).forEach { x ->
        (minPos.y .. maxPos.y).forEach { y ->
            yield(pos(x,y))
        }
    }
}

data class Point private constructor(
    val gridOrientation: Boolean = true,
    val x: Int, val y: Int) {

    override fun toString() = "($x, $y)"

    private fun above(other: Point) = if (gridOrientation) this.y < other.y else this.y > other.y
    private fun leftOf(other: Point) = this.x < other.x
    private fun oneDown() = if (gridOrientation) 1 else -1
    private fun oneUp() = -oneDown()

    private fun Direction.dXY() =
        when(this) {
            Direction.DOWN -> XYPair(0, oneDown())
            Direction.UP -> XYPair(0, oneUp())
            Direction.LEFT -> XYPair(-1,0)
            Direction.RIGHT -> XYPair(1,0)
        }

    private fun WindDirection.dXY() =
        when(this) {
            WindDirection.NORTH -> XYPair(0,oneUp())
            WindDirection.SOUTH -> XYPair(0,oneDown())
            WindDirection.EAST -> XYPair(1,0)
            WindDirection.WEST -> XYPair(-1,0)
            WindDirection.NORTHEAST -> XYPair(1,oneUp())
            WindDirection.NORTHWEST -> XYPair(-1,oneUp())
            WindDirection.SOUTHEAST -> XYPair(1,oneDown())
            WindDirection.SOUTHWEST -> XYPair(-1,oneDown())
        }


    fun plusXY(dx: Int, dy: Int) = Point(gridOrientation, x+dx, y+dy)

    fun plusX(dx: Int) = plusXY(dx, 0)
    fun plusY(dy: Int) = plusXY(0, dy)

    operator fun plus(other: Point): Point = plusXY(other.x, other.y)
    operator fun minus(other: Point): Point = plusXY(-other.x, -other.y)

    fun moveSteps(dir: Direction, steps: Int) = plusXY(steps * dir.dXY().x, steps * dir.dXY().y)
    fun moveSteps(dir: WindDirection, steps: Int) = plusXY(steps * dir.dXY().x, steps * dir.dXY().y)

    fun moveOneStep(dir: Direction) = moveSteps(dir, 1)
    fun moveOneStep(dir: WindDirection) = moveSteps(dir, 1)

    fun up() = moveOneStep(Direction.UP)
    fun down() = moveOneStep(Direction.DOWN)
    fun left() = moveOneStep(Direction.LEFT)
    fun right() = moveOneStep(Direction.RIGHT)

    fun north() = moveOneStep(WindDirection.NORTH)
    fun south() = moveOneStep(WindDirection.SOUTH)
    fun east() = moveOneStep(WindDirection.EAST)
    fun west() = moveOneStep(WindDirection.WEST)
    fun northeast() = moveOneStep(WindDirection.NORTHEAST)
    fun southeast() = moveOneStep(WindDirection.SOUTHEAST)
    fun northwest() = moveOneStep(WindDirection.NORTHWEST)
    fun southwest() = moveOneStep(WindDirection.SOUTHWEST)

    fun neighbors() = listOf(up(), down(), left(), right())
    fun allWindDirectionNeighbors() = listOf(north(), northeast(), east(), southeast(), south(), southwest(), west(), northwest())

    fun distanceTo(other: Point) = (other.x - x).absoluteValue + (other.y - y).absoluteValue

    fun directionToOrNull(other: Point) =
        if (this == other) {
            null
        } else if (this.x == other.x) {
            if (this.above(other)) Direction.DOWN else Direction.UP
        } else if (this.y == other.y) {
            if (this.leftOf(other)) Direction.RIGHT else Direction.LEFT
        } else {
            null
        }

    fun windDirectionToOrNull(other: Point) =
        if (this == other) {
            null
        } else if (this.x == other.x) {
            if (this.above(other)) WindDirection.SOUTH else WindDirection.NORTH
        } else if (this.y == other.y) {
            if (this.leftOf(other)) WindDirection.EAST else WindDirection.WEST
        } else if ((this.y - other.y).absoluteValue == (this.x - other.x).absoluteValue) {
            if (this.above(other)) {
                if (this.leftOf(other)) WindDirection.SOUTHEAST else WindDirection.SOUTHWEST
            } else {
                if (this.leftOf(other)) WindDirection.NORTHEAST else WindDirection.NORTHWEST
            }
        } else {
            null
        }

    fun rotateRightAroundOrigin() =
        if (gridOrientation) {
            Point(gridOrientation, -this.y, this.x)
        } else {
            Point(gridOrientation, this.y, -this.x)
        }

    companion object {
        fun of(gridOrientation: Boolean, input: String): Point = XYPair.of(input).run { Point(gridOrientation, this.x, this.y) }
        fun of(gridOrientation: Boolean, x: Int, y: Int) = Point(gridOrientation, x, y)
    }
}

private data class XYPair(val x: Int, val y: Int) {
    companion object {
        fun of(input: String) = input
            .removeSurrounding("<", ">")
            .removeSurrounding("(", ")")
            .removeSurrounding("[", "]")
            .removeSurrounding("{", "}")
            .split(",").run { XYPair(this[0].trim().toInt(), this[1].trim().toInt()) }
    }
}



