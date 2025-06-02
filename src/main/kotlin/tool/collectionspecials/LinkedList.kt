package tool.collectionspecials

//todo: build equals operator

open class LinkedList<T>: MutableCollection<T> {

    internal var first: LinkedListNode? = null
    internal var last: LinkedListNode? = null

    internal var localSize = 0

    override val size
        get() = localSize

    init { clear() }

    fun firstIndexOrNull(): LinkedListPointer? =
        first

    fun lastIndexOrNull(): LinkedListPointer? =
        last

    fun firstIndex(): LinkedListPointer =
        if (first != null) first!! else throw LinkedListException("List is Empty")
    fun lastIndex(): LinkedListPointer =
        if (last != null) last!! else throw LinkedListException("List is Empty")

    override fun isEmpty() =
        size == 0

    operator fun get(linkedListPointer: LinkedListPointer) =
        linkedListPointer.asDataNode().data

    operator fun set(linkedListPointer: LinkedListPointer, element:T): T {
        val node = linkedListPointer.asDataNode()
        val prevElement = node.data
        node.data = element
        return prevElement
    }

    override fun add(element: T) =
        if (first == null) addFirst(element) else addAfterLast(element)

    private fun addFirst(element: T): Boolean {
        check(first == null)

        first = newNode(element, null, null)
        last = first
        localSize++
        return true
    }

    private fun addAfterLast(element: T): Boolean {
        check (last != null)

        last!!.next = newNode(element, last, null)
        last = last!!.next
        localSize++
        return true
    }

    /**
     * Inserts an element into the list at the specified listIndex
     * assumption: the listIndex exists
     *
     */
    fun add(linkedListPointer: LinkedListPointer, element: T): Boolean {
        val node = linkedListPointer.asDataNode()

        val new = newNode(element, node.prev, node)
        node.prev = new
        val tmpPrev = new.prev
        if (tmpPrev != null) {
            tmpPrev.next = new
        } else {
            first = new
        }

        localSize++
        return true
    }

    /**
     * removes the element at 'listIndex'
     *
     * returns the data that was on this listIndex
     */
    fun removeAt(linkedListPointer: LinkedListPointer): T {
        val node = linkedListPointer.asDataNode()

        if (node == first) {
            if (node == last) {
                first = null
                last = null
            } else {
                node.next!!.prev = null
                first = node.next
            }
        } else if (node == last) {
            node.prev!!.next = null
            last = node.prev
        } else {
            node.prev!!.next = node.next
            node.next!!.prev = node.prev
        }
        localSize--

        node.decouple()

        return node.data
    }

    override fun remove(element: T): Boolean {
        val tobeRemoved = firstIndexOfOrNull(element)
        if (tobeRemoved != null) {
            removeAt(tobeRemoved)
            return true
        }
        return false
    }

    fun firstIndexOfOrNull(element: T)
        = firstIndexOfOrNullAfter(first, element)

    private fun firstIndexOfOrNullAfter(node: LinkedListNode?, element: T) : LinkedListPointer? {
        var walker = node
        while (walker != null && this[walker] != element)
            walker = walker.next
        return walker
    }


    override fun contains(element: T) =
        this.any { elt -> element == elt}


    protected open fun newNode(data: T, pprev: LinkedListNode?, pnext: LinkedListNode?) =
        DataNode(data, pprev, pnext, this)


    private fun LinkedListPointer.asDataNode(): DataNode<T> {
        if (!hasPointer(this)) {
            throw LinkedListException("Pointer is not a linkedlist data node pointer")
        }
        @Suppress("UNCHECKED_CAST")
        return this as DataNode<T>
    }

    fun hasPointer(llp: LinkedListPointer) : Boolean {
        if (llp !is DataNode<*>)
            return false

        @Suppress("UNCHECKED_CAST")
        return (llp as DataNode<T>).ownedBy(this)
    }


    override fun toString() =
        "[${this.joinToString(" <-> ")}]"

    override fun iterator(): MutableIterator<T> =
        LinkedListIterator(this)

    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        val retainElements = elements.toSet()

        var walker = first
        while (walker != null) {
            val check = walker
            walker = walker.next
            if (this[check] !in retainElements) {
                removeAt(check)
                result = true
            }
        }
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var result = false
        val removeElements = elements.toSet()

        var walker = first
        while (walker != null) {
            val check = walker
            walker = walker.next
            if (this[check] in removeElements) {
                removeAt(check)
                result = true
            }
        }
        return result
    }

    //todo: link tussen nodes en list weghalen
    final override fun clear() {
        localSize = 0
        first = null
        last = null
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elements.forEach { element -> add(element) }
        return true
    }

    override fun containsAll(elements: Collection<T>) =
        elements.toSet().all{ item -> this.contains(item) }

}

//==================================================================================================================

open class DataNode<T>(var data: T, prev: LinkedListNode?, next: LinkedListNode?, internal var owner: LinkedList<T>?): LinkedListNode(prev, next) {

    override fun toString() =
        data.toString()

    fun <T> ownedBy(ts: LinkedList<T>) =
        owner === ts

    fun decouple() {
        owner = null
        next = null
        prev = null
    }
}


