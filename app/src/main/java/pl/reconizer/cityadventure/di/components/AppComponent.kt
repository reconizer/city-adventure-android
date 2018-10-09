package pl.reconizer.cityadventure.di.components

import dagger.Component
import pl.reconizer.cityadventure.CityAdventureApp
import pl.reconizer.cityadventure.di.modules.ApiModule
import pl.reconizer.cityadventure.di.modules.ContextModule
import pl.reconizer.cityadventure.di.modules.DataModule
import pl.reconizer.cityadventure.di.modules.NetworkModule
import pl.reconizer.cityadventure.presentation.authentication.login.LoginComponent
import pl.reconizer.cityadventure.presentation.authentication.login.LoginFragment
import pl.reconizer.cityadventure.presentation.authentication.login.LoginModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    DataModule::class,
    NetworkModule::class,
    ApiModule::class
])
interface AppComponent {

    fun inject(app: CityAdventureApp)

    fun loginComponent(module: LoginModule): LoginComponent

}