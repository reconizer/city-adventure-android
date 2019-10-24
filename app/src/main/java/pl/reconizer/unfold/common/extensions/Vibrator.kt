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

fun Vibrator.errorFeedback() {
    val pattern = longArrayOf(0, 100, 50, 100, 50, 100)
    if (Build.VERSION.SDK_INT >= 26) {
        vibrate(VibrationEffect.createWaveform(pattern, -1))
    } else {
        vibrate(pattern, -1)
    }
}

fun Vibrator.successFeedback() {
    val pattern = longArrayOf(0, 50, 25, 300)
    if (Build.VERSION.SDK_INT >= 26) {
        vibrate(VibrationEffect.createWaveform(pattern, -1))
    } else {
        vibrate(pattern, -1)
    }
}

fun Context.performOneShotVibration(milliseconds: Long) {
    val vibrator = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)
    if (vibrator != null && vibrator.hasVibrator()) {
        vibrator.oneShot(milliseconds)
    }
}

fun Context.performErrorHapticFeedback() {
    val vibrator = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)
    if (vibrator != null && vibrator.hasVibrator()) {
        vibrator.errorFeedback()
    }
}

fun Context.performSuccessHapticFeedback() {
    val vibrator = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)
    if (vibrator != null && vibrator.hasVibrator()) {
        vibrator.successFeedback()
    }
}