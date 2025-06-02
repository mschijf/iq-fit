package tool.collectionspecials

interface LinkedListPointer {
    fun next(steps: Int = 1): LinkedListPointer
    fun prev(steps: Int = 1): LinkedListPointer
}