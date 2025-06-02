package ms.iqfit

val nameMap = mapOf(
    "light green" to "Lg",
    "dark blue" to "Db",
    "blue" to "Bl",
    "pink" to "Pi",
    "red" to "Re",
    "light blue" to "Lb",
    "purple" to "Pu",
    "yellow" to "Ye",
    "orange" to "Or",
    "green" to "Gr"
)

data class Piece(val name: String, val pieceStateList: List<PieceState>) {
    val shortName: String = nameMap[name]!!

    companion object {
        fun of(name: String, stateOne: List<String>, stateTwo: List<String>): Piece {
            PieceState.of(stateOne)
            PieceState.of(stateTwo)
            return Piece(
                name, listOf(
                    PieceState.of(stateOne),
                    PieceState.of(stateOne).rotateRight(),
                    PieceState.of(stateOne).rotateRight().rotateRight(),
                    PieceState.of(stateOne).rotateRight().rotateRight().rotateRight(),
                    PieceState.of(stateTwo),
                    PieceState.of(stateTwo).rotateRight(),
                    PieceState.of(stateTwo).rotateRight().rotateRight(),
                    PieceState.of(stateTwo).rotateRight().rotateRight().rotateRight(),
                )
            )
        }
    }
}