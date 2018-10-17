package pl.reconizer.cityadventure.presentation.map.game

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    GameMapModule::class
])
@ViewScope
interface GameMapComponent {

    fun inject(target: GameMapFragment)

}