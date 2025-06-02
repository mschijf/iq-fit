package tool.collectionspecials

abstract class LinkedListNode(var prev: LinkedListNode?, var next: LinkedListNode?): LinkedListPointer {

    override fun next(steps: Int): LinkedListPointer {
        if (steps < 0)
            return prev(-steps)

        var current: LinkedListNode? = this
        var stepsToDo = steps
        while (current != null && stepsToDo > 0) {
            current = current.next
            stepsToDo--
        }
        return current?:emptyNode
    }

    override fun prev(steps: Int): LinkedListPointer {
        if (steps < 0)
            return next(-steps)

        var current: LinkedListNode? = this
        var stepsToDo = steps
        while (current != null && stepsToDo > 0) {
            current = current.prev
            stepsToDo--
        }
        return current?:emptyNode
    }

    companion object {
        //todo: improve this node ??
        private val emptyNode = EmptyNode()
        private class EmptyNode: LinkedListNode(null, null)
    }
}


