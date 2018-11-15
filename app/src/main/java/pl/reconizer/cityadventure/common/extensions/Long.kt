package pl.reconizer.cityadventure.common.extensions

fun Long.toPrretyTimeString(): String {
    val days = (this / TimeConsts.SECONDS_IN_DAY).toInt()
    val hours = ((this - days * TimeConsts.SECONDS_IN_DAY) / TimeConsts.SECONDS_IN_HOUR).toInt()
    val seconds = this - days * TimeConsts.SECONDS_IN_DAY - hours * TimeConsts.SECONDS_IN_HOUR
    return if (days > 0) {
        "${days}d ${hours}h"
    } else {
        "${hours}h ${seconds}s"
    }
}