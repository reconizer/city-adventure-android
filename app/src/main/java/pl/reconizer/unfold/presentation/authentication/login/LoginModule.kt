package pl.reconizer.unfold.presentation.authentication.login

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.modules.usecases.authentication.AuthenticationUsecasesModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.usecases.authentication.SignIn
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    AuthenticationUsecasesModule::class,
    ErrorsHandlersModule::class
])
class LoginModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("main") scheduler: Scheduler,
            signInUsecase: SignIn,
            errorsHandler: ErrorsHandler
    ): LoginPresenter {
        return LoginPresenter(
                scheduler,
                signInUsecase,
                errorsHandler
        )
    }

}