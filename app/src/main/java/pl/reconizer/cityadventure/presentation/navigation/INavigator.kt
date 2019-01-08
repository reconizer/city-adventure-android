package pl.reconizer.cityadventure.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment

interface INavigator {

    fun goTo(fragment: Fragment, addOnStack: Boolean = true, transitionElement: SharedTransitionElement? = null)
    fun openOver(fragment: Fragment, transitionElement: SharedTransitionElement? = null)
    fun goBack()
    fun isRoot(): Boolean

    fun showMap(bundle: Bundle)
    fun openMapRoot(bundle: Bundle)
    fun leaveMap()

}