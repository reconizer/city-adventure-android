package pl.reconizer.unfold.common.extensions

fun <T> MutableList<T>.addIfNotExists(element: T) {
    if (element !in this) {
        add(element)
    }
}