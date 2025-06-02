package tool.primarytype

fun IntRange.overlaps(other:IntRange): Boolean {
    return (this.first in other || this.last in other || other.first in this || other.last in this)
}

fun IntRange.size(): Int {
    return this.last - this.first + 1
}