package pl.reconizer.unfold.presentation.creatorprofile

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    CreatorProfileModule::class
])
@ViewScope
interface CreatorProfileComponent {

    fun inject(target: CreatorProfileFragment)

}