package pl.reconizer.cityadventure.presentation.authentication.registration

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    RegistrationModule::class
])
@ViewScope
interface RegistrationComponent {

    fun inject(target: RegistrationFragment)

}