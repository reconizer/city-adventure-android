package pl.reconizer.cityadventure.common.extensions

fun Long.toPrettyTimeString(): String {
    val days = (this / TimeConsts.SECONDS_IN_DAY).toInt()
    val hours = ((this - days * TimeConsts.SECONDS_IN_DAY) / TimeConsts.SECONDS_IN_HOUR).toInt()
    val minutes = ((this - days * TimeConsts.SECONDS_IN_DAY - hours * TimeConsts.SECONDS_IN_HOUR) / TimeConsts.SECONDS_IN_MINUTE).toInt()
    val seconds = (this - days * TimeConsts.SECONDS_IN_DAY - hours * TimeConsts.SECONDS_IN_HOUR - minutes * TimeConsts.SECONDS_IN_MINUTE).toInt()
    return listOf(days to "d", hours to "h", minutes to "m", seconds to "s")
            .filter { it.first > 0 }
            .take(3)
            .joinToString(" ") { (value, abbr) -> "${value}${abbr}" }
}