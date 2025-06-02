package tool.collectionspecials

class FastSet64<T>(allPossibleItemsList: List<T>) {

    private val allPossibleItemsMap = if (allPossibleItemsList.size < 64)
        allPossibleItemsList.mapIndexed { index, value -> value to (1u shl index) }.toMap()
    else
        throw IndexOutOfBoundsException("Too many items for FastSet")

    private var bitSet = allPossibleItemsMap.values.sum()

    fun remove(item: T) {
        bitSet = bitSet and allPossibleItemsMap[item]!!.inv()
    }

    fun add(item: T) {
        bitSet = bitSet or allPossibleItemsMap[item]!!
    }

    fun isEmpty(): Boolean {
        return bitSet == 0u
    }

    fun contains(item: T): Boolean {
        try {
            return allPossibleItemsMap[item]!! and bitSet != 0u
        } catch (e: Exception) {
            return false
        }
    }

    override fun hashCode(): Int {
        return bitSet.toInt()
    }

    override fun equals(other: Any?): Boolean {
        return bitSet == other
    }
}
