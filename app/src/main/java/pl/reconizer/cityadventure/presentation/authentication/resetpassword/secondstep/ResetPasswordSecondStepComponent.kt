package pl.reconizer.cityadventure.presentation.authentication.resetpassword.secondstep

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    ResetPasswordSecondStepModule::class
])
@ViewScope
interface ResetPasswordSecondStepComponent {

    fun inject(target: ResetPasswordSecondStepFragment)

}