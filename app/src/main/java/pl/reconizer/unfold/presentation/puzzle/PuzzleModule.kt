package pl.reconizer.unfold.presentation.puzzle

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.location.ILocationProvider
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class PuzzleModule(
        private val adventure: MapAdventure,
        private val adventurePoint: AdventurePoint,
        private val puzzleType: PuzzleType
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            adventureRepository: IAdventureRepository,
            locationProvider: ILocationProvider,
            errorsHandler: ErrorsHandler
    ): PuzzlePresenter {
        return PuzzlePresenter(
                backgroundScheduler,
                mainScheduler,
                adventureRepository,
                locationProvider,
                errorsHandler,
                adventure,
                adventurePoint,
                puzzleType
        )
    }

}