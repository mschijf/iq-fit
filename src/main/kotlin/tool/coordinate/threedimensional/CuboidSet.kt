package tool.coordinate.threedimensional

data class CuboidSet(
    private val cuboidList: List<Cuboid> = emptyList()) {

    fun cubeCount() = cuboidList.sumOf { cuboid -> cuboid.cubeCount() }

    fun plus(newCuboid: Cuboid): CuboidSet {
        var leftOver = listOf(newCuboid)
        for (cube in cuboidList) {
            leftOver = leftOver.map{ leftOverCuboid -> leftOverCuboid.minus(cube)}.flatten()
        }
        return CuboidSet(cuboidList + leftOver)
    }

    fun minus(cuboid: Cuboid): CuboidSet {
        return CuboidSet ( cuboidList.flatMap{it.minus(cuboid)} )
    }

}
