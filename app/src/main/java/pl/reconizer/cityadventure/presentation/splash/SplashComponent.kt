package pl.reconizer.cityadventure.presentation.splash

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    SplashModule::class
])
@ViewScope
interface SplashComponent {

    fun inject(target: SplashFragment)

}