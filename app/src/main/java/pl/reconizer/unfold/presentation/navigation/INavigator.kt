package pl.reconizer.unfold.presentation.navigation

import androidx.fragment.app.Fragment
import pl.reconizer.unfold.domain.entities.MapAdventure

interface INavigator {

    fun goTo(fragment: Fragment, addOnStack: Boolean = true, transitionElement: SharedTransitionElement? = null)
    fun openOver(fragment: Fragment, transitionElement: SharedTransitionElement? = null)
    fun goBack()
    fun isRoot(): Boolean

    fun showAdventuresMap()
    fun showAdventureMap(adventure: MapAdventure, adventurePointId: String? = null)

}