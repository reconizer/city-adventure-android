package pl.reconizer.cityadventure.di

import android.content.Context
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import pl.reconizer.cityadventure.di.components.AppComponent
import pl.reconizer.cityadventure.di.components.DaggerAppComponent
import pl.reconizer.cityadventure.di.components.MainActivityComponent
import pl.reconizer.cityadventure.di.modules.ContextModule
import pl.reconizer.cityadventure.di.modules.MainActivityModule
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
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
import pl.reconizer.cityadventure.presentation.puzzle.PuzzleComponent
import pl.reconizer.cityadventure.presentation.puzzle.PuzzleModule

object Injector {

    private var appComponent: AppComponent? = null
    private var mainActivityComponent: MainActivityComponent? = null
    private var loginComponent: LoginComponent? = null
    private var gameMapComponent: GameMapComponent? = null
    private var adventureStartPointComponent: StartPointComponent? = null
    private var journalComponent: JournalComponent? = null
    private var puzzleComponent: PuzzleComponent? = null
    private var adventureSummaryComponent: AdventureSummaryComponent? = null

    fun buildAppComponent(appContext: Context): AppComponent {
        appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(appContext))
                .build()
        return appComponent!!
    }

    fun buildMainActivityComponent(@IdRes fragmentContainer: Int, activity: AppCompatActivity): MainActivityComponent {
        mainActivityComponent = appComponent!!.activityComponent(MainActivityModule(
                fragmentContainer,
                activity
        ))
        return mainActivityComponent!!
    }

    fun buildLoginComponent(): LoginComponent {
        loginComponent = mainActivityComponent!!.loginComponent(LoginModule())
        return loginComponent!!
    }

    fun buildGameMapComponent(): GameMapComponent {
        gameMapComponent = mainActivityComponent!!.gameMapComponent(GameMapModule())
        return gameMapComponent!!
    }

    fun buildAdventureStartPointComponent(adventure: Adventure): StartPointComponent {
        adventureStartPointComponent = mainActivityComponent!!.adventureStartPointComponent(
                StartPointModule(adventure)
        )
        return adventureStartPointComponent!!
    }

    fun buildJournalComponent(adventure: Adventure, adventureStartPoint: AdventureStartPoint): JournalComponent {
        journalComponent = mainActivityComponent!!.journalComponent(
                JournalModule(adventure, adventureStartPoint)
        )
        return journalComponent!!
    }

    fun buildPuzzleComponent(adventure: Adventure, adventurePoint: AdventurePoint): PuzzleComponent {
        puzzleComponent = mainActivityComponent!!.puzzleComponent(
                PuzzleModule(adventure, adventurePoint)
        )
        return puzzleComponent!!
    }

    fun buildAdventureSummaryComponent(adventure: Adventure): AdventureSummaryComponent {
        adventureSummaryComponent = mainActivityComponent!!.adventureSummaryComponent(AdventureSummaryModule(
                adventure
        ))
        return adventureSummaryComponent!!
    }

    fun clearAppComponent() {
        appComponent = null
    }

    fun clearMainActivityComponent() {
        mainActivityComponent = null
    }

    fun clearLoginComponent() {
        loginComponent = null
    }

    fun clearGameMapComponent() {
        gameMapComponent = null
    }

    fun clearAdventureStartPointComponent() {
        adventureStartPointComponent = null
    }

    fun clearJournalComponent() {
        journalComponent = null
    }

    fun clearPuzzleComponent() {
        puzzleComponent = null
    }

    fun clearAdventureSummaryComponent() {
        adventureSummaryComponent = null
    }
}