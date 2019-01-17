package pl.reconizer.cityadventure.di.components

import dagger.Subcomponent
import pl.reconizer.cityadventure.MainActivity
import pl.reconizer.cityadventure.di.modules.MainActivityModule
import pl.reconizer.cityadventure.di.scopes.ActivityScope
import pl.reconizer.cityadventure.presentation.adventure.journal.JournalComponent
import pl.reconizer.cityadventure.presentation.adventure.journal.JournalModule
import pl.reconizer.cityadventure.presentation.adventure.startpoint.StartPointComponent
import pl.reconizer.cityadventure.presentation.adventure.startpoint.StartPointModule
import pl.reconizer.cityadventure.presentation.authentication.login.LoginComponent
import pl.reconizer.cityadventure.presentation.authentication.login.LoginModule
import pl.reconizer.cityadventure.presentation.map.game.GameMapComponent
import pl.reconizer.cityadventure.presentation.map.game.GameMapModule

@Subcomponent(modules = [
    MainActivityModule::class
])
@ActivityScope
interface MainActivityComponent {

    fun inject(target: MainActivity)

    fun loginComponent(module: LoginModule): LoginComponent
    fun gameMapComponent(module: GameMapModule): GameMapComponent
    fun adventureStartPointComponent(module: StartPointModule): StartPointComponent
    fun journalComponent(module: JournalModule): JournalComponent

}