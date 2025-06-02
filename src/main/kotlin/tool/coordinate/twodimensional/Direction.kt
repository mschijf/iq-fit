package tool.coordinate.twodimensional

enum class Direction(val directionSymbol: String, val directionLetter: String) {
    UP("^", "U") {
        override fun rotateRight() = RIGHT
        override fun rotateLeft() = LEFT
    },
    DOWN("v", "D") {
        override fun rotateRight() = LEFT
        override fun rotateLeft() = RIGHT
    },
    RIGHT(">", "R") {
        override fun rotateRight() = DOWN
        override fun rotateLeft() = UP
    },
    LEFT("<", "L") {
        override fun rotateRight() = UP
        override fun rotateLeft() = DOWN
    };

    abstract fun rotateRight(): Direction
    abstract fun rotateLeft(): Direction
    override fun toString() = directionSymbol.toString()
    fun opposite() = rotateLeft().rotateLeft()

    companion object {
        fun ofSymbol(s: String): Direction =
            Direction.entries
                .firstOrNull() { it.directionSymbol == s }
                ?: throw Exception("$s is not a symbol in Direction")

        fun ofLetter(s: String): Direction =
            Direction.entries
                .firstOrNull() { it.directionLetter == s.uppercase() }
                ?: throw Exception("$s is not a letter in Direction")
    }

}
