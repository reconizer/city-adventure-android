package pl.reconizer.cityadventure.presentation.puzzle

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class PuzzleModule(
        private val adventure: Adventure,
        private val adventurePoint: AdventurePoint
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            adventureRepository: IAdventureRepository,
            errorHandler: ErrorHandler<Error>
    ): PuzzlePresenter {
        return PuzzlePresenter(
                backgroundScheduler,
                mainScheduler,
                adventureRepository,
                errorHandler,
                adventure,
                adventurePoint
        )
    }

}