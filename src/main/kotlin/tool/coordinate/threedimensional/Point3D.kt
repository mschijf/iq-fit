package tool.coordinate.threedimensional

import kotlin.math.absoluteValue

data class Point3D(val x: Int, val y: Int, val z: Int) {

    fun plusXYZ(dx: Int, dy: Int, dz: Int) = Point3D(x+dx, y+dy, z+dz)
    fun plusX(dx: Int) = plusXYZ(dx, 0, 0)
    fun plusY(dy: Int) = plusXYZ(0, dy, 0)
    fun plusZ(dz: Int) = plusXYZ(0, 0, dz)

    operator fun plus(other: Point3D): Point3D = plusXYZ(other.x, other.y, other.z)
    operator fun minus(other: Point3D): Point3D = plusXYZ(-other.x, -other.y, -other.z)

    fun distanceTo(otherPos: Point3D) = (otherPos.x - x).absoluteValue + (otherPos.y - y).absoluteValue + (otherPos.z - z).absoluteValue

    companion object {
        fun of(input: String): Point3D = input
            .removeSurrounding("<", ">")
            .removeSurrounding("(", ")")
            .removeSurrounding("[", "]")
            .removeSurrounding("{", "}")
            .split(",").run { Point3D(this[0].trim().toInt(), this[1].trim().toInt(), this[2].trim().toInt()) }

        val origin = Point3D(0,0,0)
    }

}

data class Point3DLong(val x: Long, val y: Long, val z: Long) {

    fun plusXYZ(dx: Long, dy: Long, dz: Long) = Point3DLong(x+dx, y+dy, z+dz)
    fun plusX(dx: Long) = plusXYZ(dx, 0, 0)
    fun plusY(dy: Long) = plusXYZ(0, dy, 0)
    fun plusZ(dz: Long) = plusXYZ(0, 0, dz)

    operator fun plus(other: Point3DLong): Point3DLong = plusXYZ(other.x, other.y, other.z)
    operator fun minus(other: Point3DLong): Point3DLong = plusXYZ(-other.x, -other.y, -other.z)

    fun distanceTo(otherPos: Point3DLong) = (otherPos.x - x).absoluteValue + (otherPos.y - y).absoluteValue + (otherPos.z - z).absoluteValue

    companion object {
        fun of(input: String): Point3DLong = input
            .removeSurrounding("<", ">")
            .removeSurrounding("(", ")")
            .removeSurrounding("[", "]")
            .removeSurrounding("{", "}")
            .split(",").run { Point3DLong(this[0].trim().toLong(), this[1].trim().toLong(), this[2].trim().toLong()) }

        val origin = Point3DLong(0,0,0)
    }

}
