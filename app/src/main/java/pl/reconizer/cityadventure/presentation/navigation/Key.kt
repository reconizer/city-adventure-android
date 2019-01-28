package pl.reconizer.cityadventure.presentation.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

interface Key : Parcelable {

    val fragmentTag: String
    val hasNewArguments: Boolean
        get() = false

    val arguments: Bundle?

    fun newFragment(): Fragment

}