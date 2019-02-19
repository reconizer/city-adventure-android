package pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
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