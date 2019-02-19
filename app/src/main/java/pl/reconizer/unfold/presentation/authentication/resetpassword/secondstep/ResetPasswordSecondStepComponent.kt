package pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    ResetPasswordSecondStepModule::class
])
@ViewScope
interface ResetPasswordSecondStepComponent {

    fun inject(target: ResetPasswordSecondStepFragment)

}