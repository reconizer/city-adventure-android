package pl.reconizer.cityadventure.presentation.adventure.startpoint

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    StartPointModule::class
])
@ViewScope
interface StartPointComponent {

    fun inject(target: StartPointFragment)

}