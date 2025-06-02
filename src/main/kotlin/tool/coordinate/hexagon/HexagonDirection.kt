package tool.coordinate.hexagon

enum class HexagonDirection(val directionSymbol: String) {
    NORTH("N"),
    SOUTH("S"),
    NORTHEAST("NE"),
    NORTHWEST("NW"),
    SOUTHEAST("SE"),
    SOUTHWEST("SW");

    override fun toString() = directionSymbol

    companion object {
        fun of(s: String): HexagonDirection =
            HexagonDirection
                .values()
                .firstOrNull() { it.directionSymbol == s.uppercase() }
                ?: throw Exception("$s is not a symbol in HexagonDirection")
    }
}


