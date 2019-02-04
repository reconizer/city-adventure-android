package pl.reconizer.cityadventure.presentation.menu

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    MenuModule::class
])
@ViewScope
interface MenuComponent {

    fun inject(target: MenuFragment)

}