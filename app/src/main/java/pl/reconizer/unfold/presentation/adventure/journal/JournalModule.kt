package pl.reconizer.unfold.presentation.adventure.journal

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class JournalModule(
        private val adventure: MapAdventure,
        private val adventureStartPoint: AdventureStartPoint
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            adventureRepository: IAdventureRepository,
            errorsHandler: ErrorsHandler
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