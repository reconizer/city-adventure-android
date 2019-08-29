package pl.reconizer.unfold.presentation.search.creators

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class SearchCreatorsModule(
        private val position: Position
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            creatorRepository: ICreatorRepository,
            errorsHandler: ErrorsHandler<Error>
    ): CreatorsPresenter {
        return CreatorsPresenter(
                backgroundScheduler,
                mainScheduler,
                creatorRepository,
                errorsHandler,
                position
        )
    }

    @Provides
    @ViewScope
    fun provideAdapter(): CreatorsAdapter {
        return CreatorsAdapter()
    }

}