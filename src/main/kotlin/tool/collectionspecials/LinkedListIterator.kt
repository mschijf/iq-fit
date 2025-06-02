package tool.collectionspecials

class LinkedListIterator<T>(private val owner: LinkedList<T>): MutableIterator<T> {
    private var cursor:LinkedListNode? = owner.first
    private var lastReturned:LinkedListNode? = null

    override fun hasNext() =
        cursor != null

    override fun next(): T {
        if (!hasNext())
            throw NoSuchElementException()
        val data = owner[cursor!!]
        lastReturned = cursor
        cursor = cursor!!.next
        return data
    }

    override fun remove() {
        check(lastReturned != null)
        owner.removeAt(lastReturned!!)
        lastReturned = null
    }
}
