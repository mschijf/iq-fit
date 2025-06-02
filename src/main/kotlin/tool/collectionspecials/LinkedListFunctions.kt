package tool.collectionspecials

fun <T> emptyCircularLinkedList() =
    CircularLinkedList<T>()

fun <T> Iterable<T>.toCircularLinkedList(): CircularLinkedList<T> {
    val cll = emptyCircularLinkedList<T>()
    cll.addAll(this)
    return cll
}

fun <T> circularLinkedListOf(vararg items: T): CircularLinkedList<T> {
    val ll = emptyCircularLinkedList<T>()
    ll.addAll(items)
    return ll
}

fun <T> emptyLinkedList() =
    LinkedList<T>()

fun <T> Iterable<T>.toLinkedList(): LinkedList<T> {
    val ll = emptyLinkedList<T>()
    ll.addAll(this)
    return ll
}

fun <T> linkedListOf(vararg items: T): LinkedList<T> {
    val ll = emptyLinkedList<T>()
    ll.addAll(items)
    return ll
}

