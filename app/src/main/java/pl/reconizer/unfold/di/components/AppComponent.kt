package pl.reconizer.unfold.di.components

import dagger.Component
import pl.reconizer.unfold.UnfoldApp
import pl.reconizer.unfold.di.modules.*
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

    fun inject(app: UnfoldApp)

    fun activityComponent(module: MainActivityModule): MainActivityComponent

}