package pl.reconizer.unfold.presentation.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

interface Key : Parcelable {

    val fragmentTag: String

    val isIdentifiedByTag: Boolean
        get() = false

    val hasNewArguments: Boolean
        get() = false

    val arguments: Bundle?

    fun newFragment(): Fragment

}