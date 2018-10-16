package pl.reconizer.cityadventure.presentation.navigation

import androidx.fragment.app.Fragment

interface INavigator {

    fun goTo(fragment: Fragment)
    fun goBack()
    fun goBackToRoot()
    fun isRoot(): Boolean

}