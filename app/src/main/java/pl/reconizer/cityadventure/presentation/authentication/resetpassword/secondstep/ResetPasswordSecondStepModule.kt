package pl.reconizer.cityadventure.presentation.authentication.resetpassword.secondstep

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.repositories.IUserRepository
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class ResetPasswordSecondStepModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            repository: IUserRepository,
            errorHandler: ErrorHandler<Error>
    ): ResetPasswordSecondStepPresenter {
        return ResetPasswordSecondStepPresenter(
                backgroundScheduler,
                mainScheduler,
                repository,
                errorHandler
        )
    }

}