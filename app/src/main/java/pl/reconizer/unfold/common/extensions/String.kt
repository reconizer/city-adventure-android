package pl.reconizer.unfold.common.extensions

fun prettyTimeStringRange(minLength: Long?, maxLength: Long?): String {
    return if (minLength == null) {
        "? ? ?"
    } else {
        if (maxLength == null) {
            minLength.toPrettyTimeString()
        } else {
            "${minLength.toPrettyTimeString()} - ${maxLength.toPrettyTimeString()}"
        }
    }
}