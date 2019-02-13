package pl.reconizer.unfold.presentation.splash

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    SplashModule::class
])
@ViewScope
interface SplashComponent {

    fun inject(target: SplashFragment)

}