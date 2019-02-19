package pl.reconizer.unfold.common.extensions

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.openInBrowser(url: String, failureMessage: String) {
    try {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(myIntent)
    } catch (e: Exception) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_LONG).show()
    }
}