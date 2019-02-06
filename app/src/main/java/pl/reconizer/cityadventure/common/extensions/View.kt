package pl.reconizer.cityadventure.common.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View

fun View.getDisplayMetrics(): DisplayMetrics {
    return Resources.getSystem().displayMetrics
}