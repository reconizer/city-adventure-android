package pl.reconizer.cityadventure.presentation.adventure.journal

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class JournalModule(
        private val adventure: Adventure,
        private val adventureStartPoint: AdventureStartPoint
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            adventureRepository: IAdventureRepository,
            errorHandler: ErrorHandler<Error>
    ): JournalPresenter {
        return JournalPresenter(
                backgroundScheduler,
                mainScheduler,
                adventureRepository,
                errorHandler,
                adventure,
                adventureStartPoint
        )
    }

}