package pl.reconizer.cityadventure.presentation.authentication.login

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    LoginModule::class
])
@ViewScope
interface LoginComponent {

    fun inject(target: LoginFragment)

}