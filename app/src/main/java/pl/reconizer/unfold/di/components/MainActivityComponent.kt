package pl.reconizer.unfold.di.components

import dagger.Subcomponent
import pl.reconizer.unfold.MainActivity
import pl.reconizer.unfold.di.modules.MainActivityModule
import pl.reconizer.unfold.di.scopes.ActivityScope
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
import pl.reconizer.unfold.presentation.useradventures.UserAdventuresPageComponent
import pl.reconizer.unfold.presentation.useradventures.UserAdventuresPageModule
import pl.reconizer.unfold.presentation.userprofile.UserProfileComponent
import pl.reconizer.unfold.presentation.userprofile.UserProfileModule
import pl.reconizer.unfold.presentation.userprofile.edit.EditUserProfileComponent
import pl.reconizer.unfold.presentation.userprofile.edit.EditUserProfileModule

@Subcomponent(modules = [
    MainActivityModule::class
])
@ActivityScope
interface MainActivityComponent {

    fun inject(target: MainActivity)

    fun splashComponent(module: SplashModule): SplashComponent
    fun loginComponent(module: LoginModule): LoginComponent
    fun registrationComponent(module: RegistrationModule): RegistrationComponent
    fun resetPasswordFirstStepComponent(module: ResetPasswordFirstStepModule): ResetPasswordFirstStepComponent
    fun resetPasswordSecondStepComponent(module: ResetPasswordSecondStepModule): ResetPasswordSecondStepComponent
    fun menuComponent(module: MenuModule): MenuComponent
    fun gameMapComponent(module: GameMapModule): GameMapComponent
    fun adventureStartPointComponent(module: StartPointModule): StartPointComponent
    fun journalComponent(module: JournalModule): JournalComponent
    fun puzzleComponent(module: PuzzleModule): PuzzleComponent
    fun adventureSummaryComponent(module: AdventureSummaryModule): AdventureSummaryComponent
    fun userProfileComponent(module: UserProfileModule): UserProfileComponent
    fun editUserProfileComponent(module: EditUserProfileModule): EditUserProfileComponent
    fun creatorProfileComponent(module: CreatorProfileModule): CreatorProfileComponent
    fun userAdventuresComponent(module: UserAdventuresPageModule): UserAdventuresPageComponent

}