package pl.reconizer.cityadventure.presentation.navigation

import androidx.fragment.app.Fragment
import pl.reconizer.cityadventure.domain.entities.Adventure

interface INavigator {

    fun goTo(fragment: Fragment, addOnStack: Boolean = true, transitionElement: SharedTransitionElement? = null)
    fun openOver(fragment: Fragment, transitionElement: SharedTransitionElement? = null)
    fun goBack()
    fun isRoot(): Boolean

    fun showAdventuresMap()
    fun showAdventureMap(adventure: Adventure, adventurePointId: String? = null)

}