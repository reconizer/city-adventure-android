package pl.reconizer.cityadventure.presentation.map.game

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class GameMapModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            locationProvider: ILocationProvider,
            errorHandler: ErrorHandler<Error>
    ): GameMapPresenter {
        return GameMapPresenter(
                backgroundScheduler,
                mainScheduler,
                locationProvider,
                errorHandler
        )
    }

}