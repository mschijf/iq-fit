package tool.coordinate.spiral

import tool.coordinate.twodimensional.Point
import tool.coordinate.twodimensional.xyCoordinate

private fun Int.spiralIndexToRingLevel(): Int {
    var i=1
    while (i*i < this)
        i += 2
    return (i+1)/2
}

/**
 * Spiral Index        ==>  Point ( = XYCoordinate)
 *
 * 17  16  15  14  13       (-2,  2)    (-1,  2)     (0,  2)     (1,  2)     (2,  2)
 * 18   5   4   3  12       (-2,  1)    (-1,  1)     (0,  1)     (1,  1)     (2,  1)
 * 19   6   1   2  11  ==>  (-2,  0)    (-1,  0)     (0,  0)     (1,  0)     (2,  0)
 * 20   7   8   9  10       (-2, -1)    (-1, -1)     (0, -1)     (1, -1)     (2, -1)
 * 21  22  23---> ...       (-2, -2)    (-1, -2)     (0, -2) -->
 *
 */
fun Int.spiralIndexToPoint(): Point {
    if (this < 1)
        throw Exception("Illegal spiral Number")
    if (this == 1)
        return xyCoordinate(0,0)
    val ringLevel = this.spiralIndexToRingLevel()
    val ringSquareRoot = 2*ringLevel - 1
    val ro = ringSquareRoot*ringSquareRoot
    val lo = ro - (ringSquareRoot-1)
    val lb = lo - (ringSquareRoot-1)
    val rb = lb - (ringSquareRoot-1)
    val ro2 = rb - (ringSquareRoot-1)
    val point = when (this) {
        in lo until ro -> xyCoordinate(x=this - (ro+lo)/2, y=-(ringLevel - 1))
        in lb until lo -> xyCoordinate(x=-(ringLevel - 1), y=(lb+lo)/2 - this)
        in rb until lb -> xyCoordinate(x=(rb+lb)/2 - this, y=+(ringLevel - 1))
        else -> if (this == ro) xyCoordinate(x=+(ringLevel - 1), y=-(ringLevel - 1)) else xyCoordinate(x=+(ringLevel - 1), y=this - (rb+ro2)/2)
    }
    return point
}
