package pl.reconizer.unfold.presentation.puzzle

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.PuzzleType
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.location.ILocationProvider
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class PuzzleModule(
        private val adventure: Adventure,
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
            errorsHandler: ErrorsHandler<Error>
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