package pl.reconizer.unfold.presentation.search.adventures

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class SearchAdventuresModule(
        private val position: Position
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            adventuresRepository: IAdventureRepository,
            errorsHandler: ErrorsHandler<Error>
    ): AdventuresPresenter {
        return AdventuresPresenter(
                backgroundScheduler,
                mainScheduler,
                adventuresRepository,
                errorsHandler,
                position
        )
    }

    @Provides
    @ViewScope
    fun provideAdapter(): AdventuresAdapter {
        return AdventuresAdapter()
    }

}