package pl.reconizer.unfold.presentation.authentication.registration

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.modules.usecases.authentication.AuthenticationUsecasesModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.usecases.authentication.SignUp
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    AuthenticationUsecasesModule::class,
    ErrorHandlersModule::class
])
class RegistrationModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("main") scheduler: Scheduler,
            signUpUsecase: SignUp,
            errorHandler: ErrorHandler<Error>
    ): RegistrationPresenter {
        return RegistrationPresenter(
                scheduler,
                signUpUsecase,
                errorHandler
        )
    }

}