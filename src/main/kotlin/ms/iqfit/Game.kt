package ms.iqfit

import tool.coordinate.twodimensional.Point
import tool.coordinate.twodimensional.pos
import tool.coordinate.twodimensional.printAsGrid

class Game(private val puzzleName: String) {

    private val pieces = initPieces()
    private val puzzleMap = initPuzzle()

    private fun initPieces(): List<Piece> {
        val inputLines = readFileFromResource(path="/",fileName="PieceForms")
        val pieceList = inputLines
            .filter{ it.isNotBlank() }.chunked(5)
            .map { Piece.of(it[0], it.subList(1,3),it.subList(3,5)) }
        return pieceList
    }

    private fun initPuzzle(): Map<Point, String> {
        val inputLines = readFileFromResource(path="/",fileName=puzzleName)
        return inputLines
            .flatMapIndexed { y, line ->
                line.chunked(3).mapIndexed { x, str -> pos(x,y) to str.trim() }
            }.toMap()
    }

    fun solvePuzzle() {
        puzzleMap.printAsGrid { it.padEnd(4, ' ') }
        println()

        val emptyFields = puzzleMap.filter { it.value == "." }.keys
        val usedPieces = pieces.filter {it.shortName in puzzleMap.values}

        val placedPieces = solve(emptyFields, (pieces - usedPieces).toSet(), emptyList())

        val placedPiecesMap = placedPieces.flatMap { (piece, pieceState, field) ->
            pieceState.pointList.map {pieceStatePoint -> field + pieceStatePoint to piece.shortName }
        }.toMap()
        placedPiecesMap.keys.printAsGrid { placedPiecesMap.getOrDefault(it, " ").padEnd(4, ' ') }
    }

    private fun solve(emptyFields: Set<Point>, piecesToPlace:Set<Piece>, placedPieces: List<PlacedPiece>): List<PlacedPiece> {
        if (piecesToPlace.isEmpty() && emptyFields.isEmpty()) {
            return placedPieces
        }
        if (piecesToPlace.isEmpty() || emptyFields.isEmpty()) {
            return emptyList()
        }

        val tryField = findMostDifficultField(emptyFields)
        val candidates = findMatchingPieceStateCandidates(piecesToPlace, tryField, emptyFields)
        candidates.forEach { (piece, pieceState, startField) ->
            val pieceStateFields = pieceState.pointList.map {pieceStatePoint -> startField + pieceStatePoint}
            val solution = solve(
                emptyFields - pieceStateFields,
                piecesToPlace - piece,
                placedPieces + PlacedPiece(piece, pieceState, startField),
            )
            if (solution.isNotEmpty()) {
                return solution
            }
        }

        return emptyList()
    }

    private fun findMostDifficultField(emptyFields: Set<Point>): Point {
        return emptyFields.minBy { emptyField -> emptyField.neighbors().count{ it in emptyFields } }
    }

    private fun findMatchingPieceStateCandidates(pieces:Set<Piece>, field: Point, emptyFields: Set<Point>): List<PlacedPiece> {
        val result = mutableListOf<PlacedPiece>()
        pieces.forEach { piece ->
            piece.pieceStateList.forEach { pieceState ->
                pieceState.pointList.forEach { checkPoint ->
                    val diff = field - checkPoint
                    if (pieceState.pointList.all {(it + diff) in emptyFields} ) {
                        result += PlacedPiece(piece, pieceState, diff)
                    }
                }
            }
        }
        return result
    }

    fun readFileFromResource(path: String, fileName: String)
            = javaClass.getResourceAsStream(path + fileName)!!.bufferedReader().readLines()
}

data class PlacedPiece(val piece: Piece, val pieceState: PieceState, val onBoardField: Point)