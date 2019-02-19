package pl.reconizer.unfold.presentation.map.game

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    GameMapModule::class
])
@ViewScope
interface GameMapComponent {

    fun inject(target: GameMapFragment)

}