package pl.reconizer.unfold.presentation.authentication.resetpassword.firststep

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    ResetPasswordFirstStepModule::class
])
@ViewScope
interface ResetPasswordFirstStepComponent {

    fun inject(target: ResetPasswordFirstStepFragment)

}