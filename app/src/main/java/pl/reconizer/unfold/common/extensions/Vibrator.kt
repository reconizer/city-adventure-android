package pl.reconizer.unfold.common.extensions

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun Vibrator.oneShot(milliseconds: Long) {
    if (Build.VERSION.SDK_INT >= 26) {
        vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrate(milliseconds)
    }
}

fun Context.performOneShotVibration(milliseconds: Long) {
    val vibrator = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)
    if (vibrator != null && vibrator.hasVibrator()) {
        vibrator.oneShot(milliseconds)
    }
}