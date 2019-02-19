package pl.reconizer.unfold.common.extensions

fun Long.toPrettyTimeString(): String {
    val days = (this / TimeConsts.SECONDS_IN_DAY).toInt()
    var remaining = this % TimeConsts.SECONDS_IN_DAY

    val hours = (remaining / TimeConsts.SECONDS_IN_HOUR).toInt()
    remaining %= TimeConsts.SECONDS_IN_HOUR

    val minutes = (remaining / TimeConsts.SECONDS_IN_MINUTE).toInt()
    remaining %= TimeConsts.SECONDS_IN_MINUTE

    val seconds = remaining.toInt()

    return listOf(days to "d", hours to "h", minutes to "m", seconds to "s")
            .filter { it.first > 0 }
            .take(3)
            .joinToString(" ") { (value, abbr) -> "${value}${abbr}" }
}