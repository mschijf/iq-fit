package ms.iqfit

import com.sun.swing.internal.plaf.synth.resources.synth_ja
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
        solve(emptyFields, (pieces - usedPieces).toSet(), emptyList())
    }

    fun solve(emptyFields: Set<Point>, piecesToPlace:Set<Piece>, placedPieces: List<Triple<String, PieceState, Point>>) {
        if (piecesToPlace.isEmpty() && emptyFields.isEmpty()) {
            val placedPiecesMap = placedPieces.flatMap { (shortName, pieceState, field) ->
                pieceState.pointList.map {pieceStatePoint -> field + pieceStatePoint to shortName }
            }.toMap()
            placedPiecesMap.printAsGrid(default = "") { it.padEnd(4, ' ') }
            println("==============================================")
            return
        }

        piecesToPlace.forEach { piece ->
            piece.pieceStateList.forEach { pieceState ->
                emptyFields.forEach { emptyField ->
                    if (pieceStateFitsInField(pieceState,emptyField, emptyFields)) {
                        val pieceStateFields = pieceState.pointList.map {pieceStatePoint -> emptyField + pieceStatePoint}
                        solve(emptyFields - pieceStateFields,
                            piecesToPlace - piece,
                            placedPieces+Triple(piece.shortName, pieceState, emptyField)
                        )
                    }
                }
            }
        }
    }

    private fun pieceStateFitsInField(pieceState: PieceState, field: Point, emptyFields: Set<Point>): Boolean {
        return pieceState.pointList.all {pieceStatePoint -> field + pieceStatePoint in emptyFields}
    }

    fun readFileFromResource(path: String, fileName: String)
            = javaClass.getResourceAsStream(path + fileName)!!.bufferedReader().readLines()
}

