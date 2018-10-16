package pl.reconizer.cityadventure.di.components

import dagger.Component
import pl.reconizer.cityadventure.CityAdventureApp
import pl.reconizer.cityadventure.di.modules.*
import pl.reconizer.cityadventure.presentation.authentication.login.LoginComponent
import pl.reconizer.cityadventure.presentation.authentication.login.LoginModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    SchedulersModule::class,
    DataModule::class,
    NetworkModule::class,
    ApiModule::class
])
interface AppComponent {

    fun inject(app: CityAdventureApp)

    fun activityComponent(module: MainActivityModule): MainActivityComponent

    fun loginComponent(module: LoginModule): LoginComponent

}