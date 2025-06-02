package tool.coordinate.twodimensional

enum class WindDirection(val directionLetter: String) {
    NORTH("N") {
        override fun rotateRight() = NORTHEAST
        override fun rotateLeft() = NORTHWEST
    },
    SOUTH("S") {
        override fun rotateRight() = SOUTHWEST
        override fun rotateLeft() = SOUTHEAST
    },
    WEST("W") {
        override fun rotateRight() = NORTHWEST
        override fun rotateLeft() = SOUTHWEST
    },
    EAST("E") {
        override fun rotateRight() = SOUTHEAST
        override fun rotateLeft() = NORTHEAST
    },
    NORTHEAST("NE") {
        override fun rotateRight() = EAST
        override fun rotateLeft() = NORTH
    },
    NORTHWEST("NW") {
        override fun rotateRight() = NORTH
        override fun rotateLeft() = WEST
    },
    SOUTHEAST("SE") {
        override fun rotateRight() = SOUTH
        override fun rotateLeft() = EAST
    },
    SOUTHWEST("SW") {
        override fun rotateRight() = WEST
        override fun rotateLeft() = SOUTH
    };

    abstract fun rotateRight(): WindDirection
    abstract fun rotateLeft(): WindDirection
    override fun toString() = directionLetter
    fun opposite() = rotateLeft().rotateLeft().rotateLeft().rotateLeft()

    companion object {
        fun ofLetter(s: String): WindDirection =
            WindDirection
                .values()
                .firstOrNull() { it.directionLetter == s.uppercase() }
                ?: throw Exception("$s is not a letter in WindDirection")
    }

}
