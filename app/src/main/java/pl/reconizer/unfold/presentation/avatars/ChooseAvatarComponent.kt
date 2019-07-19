package pl.reconizer.unfold.presentation.avatars

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    ChooseAvatarModule::class
])
@ViewScope
interface ChooseAvatarComponent {

    fun inject(target: ChooseAvatarFragment)

}