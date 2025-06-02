package ms.iqfit

import tool.coordinate.twodimensional.Point
import tool.coordinate.twodimensional.pos
import tool.coordinate.twodimensional.printAsGrid

class Game {

    private val pieces = initPieces()

    fun initPieces(): List<Piece> {
        val inputLines = readFileFromResource(path="/",fileName="PieceForms")
        val pieceList = inputLines
            .filter{ it.isNotBlank() }.chunked(5)
            .map { Piece.of(it[0], it.subList(1,3),it.subList(3,5)) }
        return pieceList
    }

    fun solvePuzzle(puzzleName:String) {
        val inputLines = readFileFromResource(path="/",fileName=puzzleName)
        val pointMap = inputLines
            .flatMapIndexed { y, line ->
                line.chunked(3).mapIndexed { x, str -> pos(x,y) to str.trim() }
            }.toMap()

        pointMap.printAsGrid { it.padEnd(4, ' ') }
        println()

        val emptyFields = pointMap.filter { it.value == "." }.keys
        val usedPiecesSymbols = pointMap.values.filter { it != "." }.toSet()
        val usedPieces = pieces.filter {it.shortName in usedPiecesSymbols}
        val placedPieces = solve(emptyFields, (pieces - usedPieces).toSet(), emptyList())
        val placedPiecesMap = placedPieces.flatMap { (piece, pieceState, field) ->
            pieceState.pointList.map {pieceStatePoint -> field + pieceStatePoint to piece.shortName }
        }.toMap()
        placedPiecesMap.keys.printAsGrid { placedPiecesMap.getOrDefault(it, " ").padEnd(4, ' ') }
    }

    fun solve(emptyFields: Set<Point>, piecesToPlace:Set<Piece>, placedPieces: List<PlacedPiece>): List<PlacedPiece> {
        if (piecesToPlace.isEmpty() && emptyFields.isEmpty()) {
            return placedPieces
        }
        if (piecesToPlace.isEmpty() || emptyFields.isEmpty()) {
            return emptyList()
        }

        val tryField = findMostDifficultField(emptyFields)
        val candidates = findMatchingPieceStates(piecesToPlace, tryField, emptyFields)
        candidates.forEach { (startField, piece, pieceState) ->
            val pieceStateFields = pieceState.pointList.map {pieceStatePoint -> startField + pieceStatePoint}
            val solution = solve(emptyFields - pieceStateFields,
                piecesToPlace - piece,
                placedPieces+ PlacedPiece(piece, pieceState, startField),
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

    private fun findMatchingPieceStates(pieces:Set<Piece>, field: Point, emptyFields: Set<Point>): List<Triple<Point, Piece, PieceState>> {
        val result = mutableListOf<Triple<Point, Piece, PieceState>>()
        pieces.forEach { piece ->
            piece.pieceStateList.forEach { pieceState ->
                pieceState.pointList.forEach { checkPoint ->
                    val diff = field - checkPoint
                    if (pieceState.pointList.all {(it + diff) in emptyFields} ) {
                        result += Triple(diff, piece, pieceState)
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