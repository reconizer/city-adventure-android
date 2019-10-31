package pl.reconizer.unfold.presentation.map.game

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.location.ILocationProvider
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class GameMapModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            locationProvider: ILocationProvider,
            adventureRepository: IAdventureRepository,
            userRepository: IUserRepository,
            errorsHandler: ErrorsHandler
    ): GameMapPresenter {
        return GameMapPresenter(
                backgroundScheduler,
                mainScheduler,
                locationProvider,
                adventureRepository,
                userRepository,
                errorsHandler
        )
    }

}