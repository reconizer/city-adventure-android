package pl.reconizer.unfold.presentation.authentication.registration

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    RegistrationModule::class
])
@ViewScope
interface RegistrationComponent {

    fun inject(target: RegistrationFragment)

}