package pl.reconizer.cityadventure.presentation.puzzle

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.PuzzleType
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
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
            errorHandler: ErrorHandler<Error>
    ): PuzzlePresenter {
        return PuzzlePresenter(
                backgroundScheduler,
                mainScheduler,
                adventureRepository,
                locationProvider,
                errorHandler,
                adventure,
                adventurePoint,
                puzzleType
        )
    }

}