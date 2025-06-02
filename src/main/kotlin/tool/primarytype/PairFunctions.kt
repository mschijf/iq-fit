package tool.primarytype

fun <T> Pair<T, T>.swapped() = Pair(this.second, this.first)
fun <T: Comparable<T>> Pair<T, T>.min() = if (this.first < this.second) this.first else this.second
fun <T: Comparable<T>> Pair<T, T>.max() = if (this.first > this.second) this.first else this.second