package tool.mylambdas.collectioncombination


fun <T> List<T>.asCombinedItemsSequence(): Sequence<Pair<T, T>> =
    localAsCombinedItemsSequence(this)

private fun <T> localAsCombinedItemsSequence(list: List<T>): Sequence<Pair<T, T>> = sequence {
    for (i in 0 until list.size-1)
        for (j in i+1 until list.size)
            yield( Pair(list[i], list[j]) )
}

//----------------------------------------------------------------------------------------------------------------------

inline fun <S, T> List<T>.mapCombinedItemsIndexed(destination: MutableList<S>,
                                                  combinedTransform: (index1: Int, index2: Int, item1:T, item2:T) -> S): List<S> {
    for (i in 0 until this.size-1)
        for (j in i+1 until this.size)
            destination.add(combinedTransform(i, j, this[i], this[j]))
    return destination
}

inline fun <S, T> List<T>.mapCombinedItemsIndexed(combinedTransform: (index1: Int, index2: Int, item1:T, item2:T) -> S): List<S> =
    mapCombinedItemsIndexed(mutableListOf<S>(), combinedTransform)

inline fun <S, T> List<T>.mapCombinedItems(combineOperation: (T, T) -> S): List<S> =
    mapCombinedItemsIndexed { _, _, item1, item2 -> combineOperation(item1, item2) }

fun <T> List<T>.toCombinedItemsList(): List<Pair<T, T>> =
    mapCombinedItems { t1, t2 -> Pair(t1,t2) }

inline fun <T> List<T>.filterCombinedItemsIndexed(destination: MutableList<Pair<T, T>>,
                                                  combinedPredicate: (index1: Int, index2: Int, item1:T, item2:T) -> Boolean): List<Pair<T, T>> {
    for (i in 0 until this.size-1)
        for (j in i+1 until this.size)
            if (combinedPredicate(i, j, this[i], this[j]))
                destination.add(Pair(this[i], this[j]))
    return destination
}

inline fun <T> List<T>.filterCombinedItemsIndexed(combinedPredicate: (index1: Int, index2: Int, item1:T, item2:T) -> Boolean): List<Pair<T, T>> =
    filterCombinedItemsIndexed(mutableListOf<Pair<T,T>>(), combinedPredicate)

inline fun <T> List<T>.filterCombinedItems(combinedPredicate: (T, T) -> Boolean): List<Pair<T, T>> =
    this.filterCombinedItemsIndexed {_, _, item1, item2 -> combinedPredicate(item1, item2) }


inline fun <T> List<T>.firstOrNullCombinedItems(combinedPredicate: (T, T) -> Boolean): Pair<T, T>? {
    for (i in 0 until this.size-1)
        for (j in i+1 until this.size)
            if (combinedPredicate(this[i], this[j]))
                return Pair(this[i], this[j])
    return null
}

inline fun <T> List<T>.lastOrNullCombinedItems(combinedPredicate: (T, T) -> Boolean): Pair<T, T>? {
    for (i in this.size-2 downTo 0)
        for (j in this.size-1 downTo i+1)
            if (combinedPredicate(this[i], this[j]))
                return Pair(this[i], this[j])
    return null
}


inline fun <T> List<T>.anyOfCombinedItems(combinedPredicate: (T, T) -> Boolean) =
    this.firstOrNullCombinedItems(combinedPredicate) != null


inline fun <T> List<T>.allOfCombinedItems(combinedPredicate: (T, T) -> Boolean): Boolean {
    return !this.anyOfCombinedItems { t1, t2 -> !combinedPredicate(t1, t2) }
}

inline fun <T> List<T>.noneOfCombinedItems(combinedPredicate: (T, T) -> Boolean): Boolean {
    return !this.anyOfCombinedItems(combinedPredicate)
}
