package pl.reconizer.cityadventure.presentation.authentication.login

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.modules.usecases.authentication.AuthenticationUsecasesModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.usecases.authentication.SignIn
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    AuthenticationUsecasesModule::class,
    ErrorHandlersModule::class
])
class LoginModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("main") scheduler: Scheduler,
            signInUsecase: SignIn,
            errorHandler: ErrorHandler<Error>
    ): LoginPresenter {
        return LoginPresenter(
                scheduler,
                signInUsecase,
                errorHandler
        )
    }

}