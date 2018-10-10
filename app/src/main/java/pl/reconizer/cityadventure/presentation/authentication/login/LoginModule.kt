package pl.reconizer.cityadventure.presentation.authentication.login

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.di.modules.usecases.authentication.AuthenticationUsecasesModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.usecases.authentication.SignIn
import javax.inject.Named

@Module(includes = [
    AuthenticationUsecasesModule::class
])
class LoginModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("main") scheduler: Scheduler,
            signInUsecase: SignIn
    ): LoginPresenter {
        return LoginPresenter(scheduler, signInUsecase)
    }

}