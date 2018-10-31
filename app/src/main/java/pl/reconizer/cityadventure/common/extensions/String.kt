package pl.reconizer.cityadventure.common.extensions

import android.util.Patterns

fun String.isValidAsEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}