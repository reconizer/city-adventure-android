package pl.reconizer.cityadventure.di.components

import dagger.Component
import pl.reconizer.cityadventure.CityAdventureApp
import pl.reconizer.cityadventure.di.modules.ContextModule
import pl.reconizer.cityadventure.di.modules.DataModule
import pl.reconizer.cityadventure.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    DataModule::class,
    NetworkModule::class

])
interface AppComponent {

    fun inject(app: CityAdventureApp)

}