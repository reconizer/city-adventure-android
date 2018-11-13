package pl.reconizer.cityadventure.common.extensions

fun <T : Comparable<T>> clamp(value: T, min: T, max: T): T {
    return maxOf(maxOf(min, value), minOf(max, value))
}