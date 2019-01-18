package pl.reconizer.cityadventure.presentation.puzzle

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    PuzzleModule::class
])
@ViewScope
interface PuzzleComponent {

    fun inject(target: IPuzzleView)

}