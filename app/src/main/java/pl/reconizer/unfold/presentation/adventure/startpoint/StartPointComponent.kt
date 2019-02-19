package pl.reconizer.unfold.presentation.adventure.startpoint

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    StartPointModule::class
])
@ViewScope
interface StartPointComponent {

    fun inject(target: StartPointFragment)

}