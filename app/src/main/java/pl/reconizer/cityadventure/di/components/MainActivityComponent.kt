package pl.reconizer.cityadventure.di.components

import dagger.Subcomponent
import pl.reconizer.cityadventure.MainActivity
import pl.reconizer.cityadventure.di.modules.MainActivityModule
import pl.reconizer.cityadventure.di.scopes.ActivityScope
import pl.reconizer.cityadventure.presentation.adventure.journal.JournalComponent
import pl.reconizer.cityadventure.presentation.adventure.journal.JournalModule
import pl.reconizer.cityadventure.presentation.adventure.startpoint.StartPointComponent
import pl.reconizer.cityadventure.presentation.adventure.startpoint.StartPointModule
import pl.reconizer.cityadventure.presentation.adventure.summary.AdventureSummaryComponent
import pl.reconizer.cityadventure.presentation.adventure.summary.AdventureSummaryModule
import pl.reconizer.cityadventure.presentation.authentication.login.LoginComponent
import pl.reconizer.cityadventure.presentation.authentication.login.LoginModule
import pl.reconizer.cityadventure.presentation.map.game.GameMapComponent
import pl.reconizer.cityadventure.presentation.map.game.GameMapModule
import pl.reconizer.cityadventure.presentation.menu.MenuComponent
import pl.reconizer.cityadventure.presentation.menu.MenuModule
import pl.reconizer.cityadventure.presentation.puzzle.PuzzleComponent
import pl.reconizer.cityadventure.presentation.puzzle.PuzzleModule

@Subcomponent(modules = [
    MainActivityModule::class
])
@ActivityScope
interface MainActivityComponent {

    fun inject(target: MainActivity)

    fun loginComponent(module: LoginModule): LoginComponent
    fun menuComponent(module: MenuModule): MenuComponent
    fun gameMapComponent(module: GameMapModule): GameMapComponent
    fun adventureStartPointComponent(module: StartPointModule): StartPointComponent
    fun journalComponent(module: JournalModule): JournalComponent
    fun puzzleComponent(module: PuzzleModule): PuzzleComponent
    fun adventureSummaryComponent(module: AdventureSummaryModule): AdventureSummaryComponent

}