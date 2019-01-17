package pl.reconizer.cityadventure.di.components

import dagger.Component
import pl.reconizer.cityadventure.CityAdventureApp
import pl.reconizer.cityadventure.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    UtilsModule::class,
    ContextModule::class,
    SchedulersModule::class,
    DataModule::class,
    NetworkModule::class,
    ApiModule::class
])
interface AppComponent {

    fun inject(app: CityAdventureApp)

    fun activityComponent(module: MainActivityModule): MainActivityComponent

}