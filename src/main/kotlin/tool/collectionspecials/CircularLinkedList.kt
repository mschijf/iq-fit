package tool.collectionspecials

class CircularLinkedList<T>: LinkedList<T>() {

    override fun toString() =
        "@[ ${this.joinToString(" <-> ")} ]@"

    override fun newNode(data: T, pprev: LinkedListNode?, pnext: LinkedListNode?) =
        CircularLinkedListNode(data, pprev, pnext, this)

}

class CircularLinkedListNode<T>(data: T, prev: LinkedListNode?, next: LinkedListNode?, owner: CircularLinkedList<T>): DataNode<T>(data, prev, next, owner) {

    override fun next(steps: Int): LinkedListPointer {
        if (steps < 0)
            return prev(-steps)

        val list = owner?:return super.next(steps)
        var current: LinkedListNode? = this
        var stepsToDo = steps % list.size
        while (current != null && stepsToDo > 0) {
            current = if (current.next == null) list.first else current.next
            stepsToDo--
        }
        return current!!
    }

    override fun prev(steps: Int): LinkedListPointer {
        if (steps < 0)
            return next(-steps)

        val list = owner?:return super.prev(steps)
        var current: LinkedListNode? = this
        var stepsToDo = steps % list.size
        while (current != null && stepsToDo > 0) {
            current = if (current.prev == null) list.last else current.prev
            stepsToDo--
        }
        return current!!
    }
}
