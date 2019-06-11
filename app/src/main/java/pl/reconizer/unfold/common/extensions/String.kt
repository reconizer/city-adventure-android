package pl.reconizer.unfold.common.extensions

fun prettyTimeStringRange(minLength: Long, maxLength: Long): String {
    return if (minLength > 0 && maxLength > 0) {
        if (minLength == maxLength) {
            minLength.toPrettyTimeString()
        } else {
            "${minLength.toPrettyTimeString()} - ${maxLength.toPrettyTimeString()}"
        }
    } else {
        "? ? ?"
    }
}