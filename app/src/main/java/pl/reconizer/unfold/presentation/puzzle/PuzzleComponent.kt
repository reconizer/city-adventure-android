package pl.reconizer.unfold.presentation.puzzle

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    PuzzleModule::class
])
@ViewScope
interface PuzzleComponent {

    fun inject(target: BasePuzzleFragment)

}