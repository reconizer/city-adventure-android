package pl.reconizer.unfold.presentation.creatorprofile

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class CreatorProfileModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            creatorRepository: ICreatorRepository,
            errorsHandler: ErrorsHandler<Error>
    ): CreatorProfilePresenter {
        return CreatorProfilePresenter(
                backgroundScheduler,
                mainScheduler,
                creatorRepository,
                errorsHandler
        )
    }


}