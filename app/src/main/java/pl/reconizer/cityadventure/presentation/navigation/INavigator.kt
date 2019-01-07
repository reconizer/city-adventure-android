package pl.reconizer.cityadventure.presentation.navigation

import androidx.fragment.app.Fragment

interface INavigator {

    fun open(fragment: Fragment)
    fun goTo(fragment: Fragment, addOnStack: Boolean = true, transitionElement: SharedTransitionElement? = null)
    fun openOver(fragment: Fragment, transitionElement: SharedTransitionElement? = null)
    fun goBack()
    fun goBackToRoot()
    fun isRoot(): Boolean

}