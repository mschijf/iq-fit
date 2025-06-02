package tool.coordinate.threedimensional

import java.lang.Long.max
import java.lang.Long.min

data class Cuboid(
    private val minX: Long,
    private val maxX: Long,
    private val minY: Long,
    private val maxY: Long,
    private val minZ: Long,
    private val maxZ: Long) {

    init {
        if (minX > maxX || minY > maxY || minZ > maxZ) {
            throw Exception("Trying to build illegal cuboid:  (X:$minX..$maxX  Y:$minY..$maxY  Z:$minZ..$maxZ)")
        }
    }

    fun cubeCount() = (1 + maxX - minX) * (1 + maxY - minY) * (1 + maxZ - minZ)

    fun intersectionOrNull(other: Cuboid?): Cuboid? {
        if (other == null)
            return null

        val iMinX = max(minX, other.minX)
        val iMaxX = min(maxX, other.maxX)
        val iMinY = max(minY, other.minY)
        val iMaxY = min(maxY, other.maxY)
        val iMinZ = max(minZ, other.minZ)
        val iMaxZ = min(maxZ, other.maxZ)
        return if (iMinX <= iMaxX && iMinY <= iMaxY && iMinZ <= iMaxZ)
            Cuboid(iMinX, iMaxX, iMinY, iMaxY, iMinZ,  iMaxZ)
        else
            null
    }

    fun plus(other: Cuboid?): List<Cuboid> {
        if (other == null)
            return listOf(this)

        val intersection = intersectionOrNull(other) ?: return listOf(this, other)

        return this.minus(intersection) + other.minus(intersection) + listOf(intersection)
    }

    fun minus(other: Cuboid?): List<Cuboid> {
        if (other == null)
            return listOf(this)

        val intersection = intersectionOrNull(other) ?: return listOf(this)

        var minX = minX
        var maxX = maxX
        var minY = minY
        var maxY = maxY

        val result = mutableListOf<Cuboid>()
        if (minX < intersection.minX) {
            result.add(Cuboid(minX, intersection.minX-1, minY, maxY, minZ, maxZ))
            minX = intersection.minX
        }
        if (intersection.maxX < maxX) {
            result.add(Cuboid(intersection.maxX+1, maxX, minY, maxY, minZ, maxZ))
            maxX = intersection.maxX
        }
        if (minY < intersection.minY) {
            result.add(Cuboid(minX, maxX, minY, intersection.minY-1, minZ, maxZ))
            minY = intersection.minY
        }
        if (intersection.maxY < maxY) {
            result.add(Cuboid(minX, maxX, intersection.maxY+1, maxY, minZ, maxZ))
            maxY = intersection.maxY
        }
        if (minZ < intersection.minZ) {
            result.add(Cuboid(minX, maxX, minY, maxY, minZ, intersection.minZ-1))
        }
        if (intersection.maxZ < maxZ) {
            result.add(Cuboid(minX, maxX, minY, maxY, intersection.maxZ+1, maxZ))
        }
        return result
    }
}
