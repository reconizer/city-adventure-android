package pl.reconizer.cityadventure.presentation.authentication.registration

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.modules.usecases.authentication.AuthenticationUsecasesModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.usecases.authentication.SignUp
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
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