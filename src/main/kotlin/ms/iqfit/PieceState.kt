package ms.iqfit

import tool.coordinate.twodimensional.Point
import tool.coordinate.twodimensional.pos

data class PieceState(val pointList: List<Point>) {
    companion object {
        fun of(input: List<String>) : PieceState {
            assert(input.size == 2)
            assert(input[0].length in 3..4)
            assert(input[1].length in 3..4)

            val pointMap = input
                .flatMapIndexed { y, line ->
                    line.mapIndexed { x, ch -> pos(x,y) to ch  }
                }.toMap()

            return PieceState(pointMap.filter { it.value == 'x' }.keys.toList())
        }
    }

    fun rotateRight() : PieceState {
        return PieceState(this.pointList.map { it.rotateRightAroundOrigin() })
    }
}