package pl.reconizer.unfold.presentation.authentication.login

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    LoginModule::class
])
@ViewScope
interface LoginComponent {

    fun inject(target: LoginFragment)

}