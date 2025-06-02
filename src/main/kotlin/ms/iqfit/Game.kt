package ms.iqfit

import tool.coordinate.twodimensional.Point
import tool.coordinate.twodimensional.pos
import tool.coordinate.twodimensional.printAsGrid

class Game() {

    private val pieces = initPieces()

    private fun initPieces(): List<Piece> {
        val inputLines = readFileFromResource(path="/",fileName="PieceForms")
        val pieceList = inputLines
            .filter{ it.isNotBlank() }.chunked(5)
            .map { Piece.of(it[0], it.subList(1,3),it.subList(3,5)) }
        return pieceList
    }

    fun solvePuzzle(puzzleName: String) {
        val puzzleMap = fileToPuzzleMap(puzzleName)
        puzzleMap.printAsGrid { it.padEnd(4, ' ') }
        println()

        val emptyFields = puzzleMap.filter { it.value == "." }.keys
        val usedPieces = pieces.filter {it.shortName in puzzleMap.values}

//        val placedPieces = solveFirst(emptyFields, (pieces - usedPieces).toSet(), emptyList())
//
//        val placedPiecesMap = placedPieces.flatMap { (piece, pieceState, field) ->
//            pieceState.pointList.map {pieceStatePoint -> field + pieceStatePoint to piece.shortName }
//        }.toMap()
//        placedPiecesMap.keys.printAsGrid { placedPiecesMap.getOrDefault(it, " ").padEnd(4, ' ') }

        val result = solveAll(emptyFields, (pieces - usedPieces).toSet())
        result.forEachIndexed { idx, placedPieces ->
            println("solution ${idx+1}: --------------------------------------------------")
            println()
            val placedPiecesMap = placedPieces.flatMap { (piece, pieceState, field) ->
                pieceState.pointList.map {pieceStatePoint -> field + pieceStatePoint to piece.shortName }
            }.toMap()
            placedPiecesMap.keys.printAsGrid { placedPiecesMap.getOrDefault(it, " ").padEnd(4, ' ') }
        }
        println("===============================================================")
    }

    fun countAllPossibilities() {
        val startTime = System.currentTimeMillis()
        println("start counting all")
        val allEmpty = (0..9).flatMap{x -> (0..4).map{ y -> pos(x,y)}}.toSet()
        val allPossibilities = countAll(allEmpty, pieces.toSet())
        val timePassed = System.currentTimeMillis() - startTime
        print("All possibilities: $allPossibilities (after %d.%03d sec)".format(timePassed / 1000, timePassed % 1000))
        println()
    }


    private fun solveFirst(emptyFields: Set<Point>, piecesToPlace:Set<Piece>, placedPieces: List<PlacedPiece>): List<PlacedPiece> {
        if (piecesToPlace.isEmpty() && emptyFields.isEmpty()) {
            return placedPieces
        }
        if (piecesToPlace.isEmpty() || emptyFields.isEmpty()) {
            return emptyList()
        }

        val tryField = emptyFields.findMostDifficultField()
        val candidates = findMatchingPieceStateCandidates(piecesToPlace, tryField, emptyFields)
        candidates.forEach { (piece, pieceState, startField) ->
            val pieceStateFields = pieceState.pointList.map {pieceStatePoint -> startField + pieceStatePoint}
            val solution = solveFirst(
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

    private fun solveAll(emptyFields: Set<Point>, piecesToPlace:Set<Piece>): List<List<PlacedPiece>> {
        if (piecesToPlace.isEmpty() && emptyFields.isEmpty()) {
            return listOf(emptyList())
        }
        if (piecesToPlace.isEmpty() || emptyFields.isEmpty()) {
            return emptyList()
        }

        val tryField = emptyFields.findMostDifficultField()
        val candidates = findMatchingPieceStateCandidates(piecesToPlace, tryField, emptyFields)
        val resultList = mutableListOf<List<PlacedPiece>>()
        candidates.forEach { candidate ->
            val pieceStateFields = candidate.pieceState.pointList.map {pieceStatePoint -> candidate.onBoardField + pieceStatePoint}
            val solution = solveAll(
                emptyFields - pieceStateFields,
                piecesToPlace - candidate.piece
            )
            if (solution.isNotEmpty())
                resultList += solution.map { placedPieces -> placedPieces + candidate }
        }

        return resultList
    }

    private fun Point.availableNeigborsCount(emptyFields: Set<Point>): Int {
        return this.neighbors().count{ it in emptyFields }
    }

    private fun Set<Point>.findMostDifficultField(): Point {
        return this.minBy { it.availableNeigborsCount(this) }
    }

    private fun findMatchingPieceStateCandidates(pieces:Set<Piece>, field: Point, emptyFields: Set<Point>): List<PlacedPiece> {
        if (field.availableNeigborsCount(emptyFields) == 0)
            return emptyList()

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


    //
    // All possibilities: 301350 (after 21.041 sec)
    //
    var runCount = 0L
    val cache = mutableMapOf<Pair<Set<Point>, Set<Piece>>, Long>()
    private fun countAll(emptyFields: Set<Point>, piecesToPlace:Set<Piece>): Long {
        if (piecesToPlace.isEmpty() ) {
            if (emptyFields.isEmpty())
                return 1
            return 0
        }

        val cacheKey = Pair(emptyFields, piecesToPlace)
        if (cache.containsKey(cacheKey)) {
            return cache[cacheKey]!!
        }

        if (piecesToPlace.sumOf { it.maxPins } < emptyFields.size || piecesToPlace.sumOf { it.minPins } > emptyFields.size) {
            return 0
        }

        val tryField = emptyFields.findMostDifficultField()
        val candidates = findMatchingPieceStateCandidates(piecesToPlace, tryField, emptyFields)
        var total = 0L
        candidates.forEach { candidate ->

            if (piecesToPlace.size == 10) {
                print(runCount++)
            }
            if (piecesToPlace.size == 9) {
                print(".")
            }

            val pieceStateFields = candidate.pieceState.pointList.map {pieceStatePoint -> candidate.onBoardField + pieceStatePoint}
            val solutions = countAll(
                emptyFields - pieceStateFields,
                piecesToPlace - candidate.piece
            )
            total += solutions
            if (piecesToPlace.size == 10) {
                println("($solutions)")
            }

        }
        cache[cacheKey] = total

        return total
    }

    private fun fileToPuzzleMap(puzzleName: String): Map<Point, String> {
        val inputLines = readFileFromResource(path="/",fileName=puzzleName)
        return inputLines
            .flatMapIndexed { y, line ->
                line.chunked(3).mapIndexed { x, str -> pos(x,y) to str.trim() }
            }.toMap()
    }

    fun readFileFromResource(path: String, fileName: String)
            = javaClass.getResourceAsStream(path + fileName)!!.bufferedReader().readLines()
}

data class PlacedPiece(val piece: Piece, val pieceState: PieceState, val onBoardField: Point)