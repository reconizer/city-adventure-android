package pl.reconizer.cityadventure.common.extensions

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import pl.reconizer.cityadventure.R

fun Resources.getStringByName(context: Context, resourceName: String, @StringRes defaultResource: Int = R.string.no_translation): String {
    val packageName = context.packageName
    val resId = getIdentifier(resourceName.toLowerCase(), "string", packageName)
    return when(resId) {
        0 -> getString(defaultResource)
        else -> getString(resId)
    }
}

fun Resources.getStringByNameBang(context: Context, resourceName: String): String {
    val packageName = context.packageName
    val resId = getIdentifier(resourceName.toLowerCase(), "string", packageName)
    return when(resId) {
        0 -> throw IllegalArgumentException("Wrong resource id")
        else -> getString(resId)
    }
}

fun Resources.getDrawableResId(context: Context, drawableName: String): Int? {
    val packageName = context.packageName
    val resId = getIdentifier(drawableName, "drawable", packageName)
    return if (resId == 0) null else resId
}