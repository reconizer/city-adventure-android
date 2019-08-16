package pl.reconizer.unfold.presentation.userprofile.edit

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    EditUserProfileModule::class
])
@ViewScope
interface EditUserProfileComponent {

    fun inject(target: EditUserProfileFragment)

}