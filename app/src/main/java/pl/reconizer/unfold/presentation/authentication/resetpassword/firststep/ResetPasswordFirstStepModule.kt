package pl.reconizer.unfold.presentation.authentication.resetpassword.firststep

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class ResetPasswordFirstStepModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            repository: IUserRepository,
            errorsHandler: ErrorsHandler
    ): ResetPasswordFirstStepPresenter {
        return ResetPasswordFirstStepPresenter(
                backgroundScheduler,
                mainScheduler,
                repository,
                errorsHandler
        )
    }

}