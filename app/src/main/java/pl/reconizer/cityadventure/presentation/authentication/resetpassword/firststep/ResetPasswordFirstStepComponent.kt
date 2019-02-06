package pl.reconizer.cityadventure.presentation.authentication.resetpassword.firststep

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    ResetPasswordFirstStepModule::class
])
@ViewScope
interface ResetPasswordFirstStepComponent {

    fun inject(target: ResetPasswordFirstStepFragment)

}