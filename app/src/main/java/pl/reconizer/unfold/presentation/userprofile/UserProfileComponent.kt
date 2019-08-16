package pl.reconizer.unfold.presentation.userprofile

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    UserProfileModule::class
])
@ViewScope
interface UserProfileComponent {

    fun inject(target: UserProfileFragment)

}