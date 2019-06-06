package pl.reconizer.unfold.di

import android.content.Context
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import pl.reconizer.unfold.di.components.AppComponent
import pl.reconizer.unfold.di.components.DaggerAppComponent
import pl.reconizer.unfold.di.components.MainActivityComponent
import pl.reconizer.unfold.di.modules.ContextModule
import pl.reconizer.unfold.di.modules.MainActivityModule
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.entities.PuzzleType
import pl.reconizer.unfold.presentation.adventure.journal.JournalComponent
import pl.reconizer.unfold.presentation.adventure.journal.JournalModule
import pl.reconizer.unfold.presentation.adventure.startpoint.StartPointComponent
import pl.reconizer.unfold.presentation.adventure.startpoint.StartPointModule
import pl.reconizer.unfold.presentation.adventure.summary.AdventureSummaryComponent
import pl.reconizer.unfold.presentation.adventure.summary.AdventureSummaryModule
import pl.reconizer.unfold.presentation.authentication.login.LoginComponent
import pl.reconizer.unfold.presentation.authentication.login.LoginModule
import pl.reconizer.unfold.presentation.authentication.registration.RegistrationComponent
import pl.reconizer.unfold.presentation.authentication.registration.RegistrationModule
import pl.reconizer.unfold.presentation.authentication.resetpassword.firststep.ResetPasswordFirstStepComponent
import pl.reconizer.unfold.presentation.authentication.resetpassword.firststep.ResetPasswordFirstStepModule
import pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep.ResetPasswordSecondStepComponent
import pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep.ResetPasswordSecondStepModule
import pl.reconizer.unfold.presentation.creatorprofile.CreatorProfileComponent
import pl.reconizer.unfold.presentation.creatorprofile.CreatorProfileModule
import pl.reconizer.unfold.presentation.map.game.GameMapComponent
import pl.reconizer.unfold.presentation.map.game.GameMapModule
import pl.reconizer.unfold.presentation.menu.MenuComponent
import pl.reconizer.unfold.presentation.menu.MenuModule
import pl.reconizer.unfold.presentation.puzzle.PuzzleComponent
import pl.reconizer.unfold.presentation.puzzle.PuzzleModule
import pl.reconizer.unfold.presentation.splash.SplashComponent
import pl.reconizer.unfold.presentation.splash.SplashModule
import pl.reconizer.unfold.presentation.userprofile.UserProfileComponent
import pl.reconizer.unfold.presentation.userprofile.UserProfileModule
import pl.reconizer.unfold.presentation.userprofile.edit.EditUserProfileComponent
import pl.reconizer.unfold.presentation.userprofile.edit.EditUserProfileModule

object Injector {

    private var appComponent: AppComponent? = null
    private var mainActivityComponent: MainActivityComponent? = null
    private var splashComponent: SplashComponent? = null
    private var loginComponent: LoginComponent? = null
    private var registrationComponent: RegistrationComponent? = null
    private var resetPasswordFirstStepComponent: ResetPasswordFirstStepComponent? = null
    private var resetPasswordSecondStepComponent: ResetPasswordSecondStepComponent? = null
    private var menuComponent: MenuComponent? = null
    private var gameMapComponent: GameMapComponent? = null
    private var adventureStartPointComponent: StartPointComponent? = null
    private var journalComponent: JournalComponent? = null
    private var puzzleComponent: PuzzleComponent? = null
    private var adventureSummaryComponent: AdventureSummaryComponent? = null
    private var userProfileComponent: UserProfileComponent? = null
    private var editUserProfileComponent: EditUserProfileComponent? = null
    private var creatorProfileComponent: CreatorProfileComponent? = null

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

    fun buildSplashComponent(): SplashComponent {
        splashComponent = mainActivityComponent!!.splashComponent(SplashModule())
        return splashComponent!!
    }

    fun buildLoginComponent(): LoginComponent {
        loginComponent = mainActivityComponent!!.loginComponent(LoginModule())
        return loginComponent!!
    }

    fun buildRegistrationComponent(): RegistrationComponent {
        registrationComponent = mainActivityComponent!!.registrationComponent(RegistrationModule())
        return registrationComponent!!
    }

    fun buildResetPasswordFirstStepComponent(): ResetPasswordFirstStepComponent {
        resetPasswordFirstStepComponent = mainActivityComponent!!.resetPasswordFirstStepComponent(ResetPasswordFirstStepModule())
        return resetPasswordFirstStepComponent!!
    }

    fun buildResetPasswordSecondStepComponent(): ResetPasswordSecondStepComponent {
        resetPasswordSecondStepComponent = mainActivityComponent!!.resetPasswordSecondStepComponent(ResetPasswordSecondStepModule())
        return resetPasswordSecondStepComponent!!
    }

    fun buildMenuComponent(): MenuComponent {
        menuComponent = mainActivityComponent!!.menuComponent(MenuModule())
        return menuComponent!!
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

    fun buildPuzzleComponent(
            adventure: Adventure,
            adventurePoint: AdventurePoint,
            puzzleType: PuzzleType
    ): PuzzleComponent {
        puzzleComponent = mainActivityComponent!!.puzzleComponent(
                PuzzleModule(adventure, adventurePoint, puzzleType)
        )
        return puzzleComponent!!
    }

    fun buildAdventureSummaryComponent(adventure: Adventure): AdventureSummaryComponent {
        adventureSummaryComponent = mainActivityComponent!!.adventureSummaryComponent(AdventureSummaryModule(
                adventure
        ))
        return adventureSummaryComponent!!
    }

    fun buildUserProfileComponent(): UserProfileComponent {
        userProfileComponent = mainActivityComponent!!.userProfileComponent(UserProfileModule())
        return userProfileComponent!!
    }

    fun buildEditUserProfileComponent(): EditUserProfileComponent {
        editUserProfileComponent = mainActivityComponent!!.editUserProfileComponent(EditUserProfileModule())
        return editUserProfileComponent!!
    }

    fun buildCreatorProfileComponent(): CreatorProfileComponent {
        creatorProfileComponent = mainActivityComponent!!.creatorProfileComponent(CreatorProfileModule())
        return creatorProfileComponent!!
    }

    fun clearAppComponent() {
        appComponent = null
    }

    fun clearMainActivityComponent() {
        mainActivityComponent = null
    }

    fun clearSplashComponent() {
        splashComponent = null
    }

    fun clearLoginComponent() {
        loginComponent = null
    }

    fun clearRegistrationComponent() {
        registrationComponent = null
    }

    fun clearResetPasswordFirstStepComponent() {
        resetPasswordFirstStepComponent = null
    }

    fun clearResetPasswordSecondStepComponent() {
        resetPasswordSecondStepComponent = null
    }

    fun clearMenuComponent() {
        menuComponent = null
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

    fun clearUserProfileComponent() {
        userProfileComponent = null
    }

    fun clearEditUserProfileComponent() {
        editUserProfileComponent = null
    }

    fun clearCreatorProfileComponent() {
        creatorProfileComponent = null
    }

}