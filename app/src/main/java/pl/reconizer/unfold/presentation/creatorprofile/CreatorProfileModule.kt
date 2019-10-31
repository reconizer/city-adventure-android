package pl.reconizer.unfold.presentation.creatorprofile

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.creatorprofile.adventures.CreatorAdventuresAdapter
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class CreatorProfileModule(
        private val creatorId: String
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            creatorRepository: ICreatorRepository,
            errorsHandler: ErrorsHandler
    ): CreatorProfilePresenter {
        return CreatorProfilePresenter(
                backgroundScheduler,
                mainScheduler,
                creatorRepository,
                errorsHandler,
                creatorId
        )
    }

    @Provides
    @ViewScope
    fun provideAdapter(): CreatorAdventuresAdapter {
        return CreatorAdventuresAdapter()
    }

}