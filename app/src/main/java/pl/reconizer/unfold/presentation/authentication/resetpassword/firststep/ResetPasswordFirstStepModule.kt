package pl.reconizer.unfold.presentation.authentication.resetpassword.firststep

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
class ResetPasswordFirstStepModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            repository: IUserRepository,
            errorHandler: ErrorHandler<Error>
    ): ResetPasswordFirstStepPresenter {
        return ResetPasswordFirstStepPresenter(
                backgroundScheduler,
                mainScheduler,
                repository,
                errorHandler
        )
    }

}