package pl.reconizer.unfold.presentation.useradventures

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    UserAdventuresPageModule::class
])
@ViewScope
interface UserAdventuresPageComponent {

    fun inject(target: UserAdventuresPageFragment)

}