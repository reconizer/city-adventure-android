package pl.reconizer.unfold.common.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboard(targetView: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.showSoftInput(targetView, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hideKeyboard(view: View?) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
}