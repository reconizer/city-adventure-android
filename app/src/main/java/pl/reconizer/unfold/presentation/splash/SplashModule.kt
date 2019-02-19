package pl.reconizer.unfold.presentation.splash

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IAuthenticationRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class SplashModule() {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            repository: IAuthenticationRepository,
            errorHandler: ErrorHandler<Error>
    ): SplashPresenter {
        return SplashPresenter(
                backgroundScheduler,
                mainScheduler,
                repository,
                errorHandler
        )
    }

}