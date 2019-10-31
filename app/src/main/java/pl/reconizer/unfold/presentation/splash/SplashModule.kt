package pl.reconizer.unfold.presentation.splash

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IAuthenticationRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class SplashModule() {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            repository: IAuthenticationRepository,
            errorsHandler: ErrorsHandler
    ): SplashPresenter {
        return SplashPresenter(
                backgroundScheduler,
                mainScheduler,
                repository,
                errorsHandler
        )
    }

}