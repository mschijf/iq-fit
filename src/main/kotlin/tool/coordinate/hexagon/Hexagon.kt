package tool.coordinate.hexagon

import kotlin.math.absoluteValue
import kotlin.math.max

//
// See: https://math.stackexchange.com/questions/2254655/hexagon-grid-coordinate-system
//  or: http://redblobgames.com/grids/hexagons
//
// Simply put: a hexagon has three axes:
//             one in a  line where the x-coordinate stays the same. y and z will increase resp. decrease
//             one in a  line where the y-coordinate stays the same. x and z will increase resp. decrease
//             one in a  line where the z-coordinate stays the same. x and y will increase resp. decrease
//

data class Hexagon (val x: Int, val y: Int, val z: Int){
    fun moveOneStep(direction: HexagonDirection) = Hexagon(x + direction.dX(), y + direction.dY(), z + direction.dZ())
    fun getNeighbours(): List<Hexagon> =  listOf(north(), northeast(), southeast(), south(), southwest(), northwest() )

    fun north() = moveOneStep( HexagonDirection.NORTH)
    fun south() = moveOneStep( HexagonDirection.SOUTH)
    fun northeast() = moveOneStep( HexagonDirection.NORTHEAST)
    fun southeast() = moveOneStep( HexagonDirection.SOUTHEAST)
    fun northwest() = moveOneStep( HexagonDirection.NORTHWEST)
    fun southwest() = moveOneStep( HexagonDirection.SOUTHWEST)

    fun distanceTo(other: Hexagon) =
        max((x-other.x).absoluteValue, max((y-other.y).absoluteValue, (z-other.z).absoluteValue))

    private fun HexagonDirection.dX(): Int =
        when(this) {
            HexagonDirection.NORTH -> 0
            HexagonDirection.SOUTH -> 0
            HexagonDirection.NORTHEAST -> +1
            HexagonDirection.SOUTHWEST -> -1
            HexagonDirection.NORTHWEST -> -1
            HexagonDirection.SOUTHEAST -> +1
        }

    private fun HexagonDirection.dY(): Int =
        when (this) {
            HexagonDirection.NORTH -> -1
            HexagonDirection.SOUTH ->  1
            HexagonDirection.NORTHEAST -> -1
            HexagonDirection.SOUTHWEST -> +1
            HexagonDirection.NORTHWEST -> 0
            HexagonDirection.SOUTHEAST -> 0
        }

    private fun HexagonDirection.dZ(): Int =
        when (this) {
            HexagonDirection.NORTH ->  1
            HexagonDirection.SOUTH -> -1
            HexagonDirection.NORTHEAST -> 0
            HexagonDirection.SOUTHWEST -> 0
            HexagonDirection.NORTHWEST -> +1
            HexagonDirection.SOUTHEAST -> -1
        }

    companion object {
        val origin = Hexagon(0,0,0)
    }
}

