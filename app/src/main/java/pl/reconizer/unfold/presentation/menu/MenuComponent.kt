package pl.reconizer.unfold.presentation.menu

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    MenuModule::class
])
@ViewScope
interface MenuComponent {

    fun inject(target: MenuFragment)

}