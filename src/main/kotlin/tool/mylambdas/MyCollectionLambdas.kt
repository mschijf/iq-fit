package tool.mylambdas

fun <T> Iterable<T>.splitByCondition(predicate: (T) -> Boolean): List<List<T>> {
    val result = ArrayList<List<T>>()
    var tmp = mutableListOf<T>()
    this.forEach {
        if (predicate(it)) {
            result.add(tmp)
            tmp = mutableListOf()
        } else {
            tmp.add(it)
        }
    }
    result.add(tmp)
    return result
}

/**
 * Om te kijken of er overlap is tussen twee sets, kun je kijken de doosnede van de twee sets leeg is, maar sneller is het
 * om te kijken of er een element van de ene set, in de andere set zit.
 */
fun <T> Set<T>.intersects(other: Set<T>) = other.any{this.contains(it)}
fun <T> Set<T>.disjoins(other: Set<T>) = other.none{this.contains(it)}
