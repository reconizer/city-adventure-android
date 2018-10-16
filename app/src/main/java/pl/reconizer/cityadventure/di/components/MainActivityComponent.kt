package pl.reconizer.cityadventure.di.components

import dagger.Subcomponent
import pl.reconizer.cityadventure.MainActivity
import pl.reconizer.cityadventure.di.modules.MainActivityModule
import pl.reconizer.cityadventure.di.scopes.ActivityScope

@Subcomponent(modules = [
    MainActivityModule::class
])
@ActivityScope
interface MainActivityComponent {

    fun inject(target: MainActivity)

}