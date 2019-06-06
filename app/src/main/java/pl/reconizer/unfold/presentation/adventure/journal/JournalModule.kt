package pl.reconizer.unfold.presentation.adventure.journal

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
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
            errorsHandler: ErrorsHandler<Error>
    ): JournalPresenter {
        return JournalPresenter(
                backgroundScheduler,
                mainScheduler,
                adventureRepository,
                errorsHandler,
                adventure,
                adventureStartPoint
        )
    }

}