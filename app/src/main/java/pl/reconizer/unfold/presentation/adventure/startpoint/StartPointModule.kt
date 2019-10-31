package pl.reconizer.unfold.presentation.adventure.startpoint

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.customviews.ShadowGenerator
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class StartPointModule(
        private val adventure: MapAdventure
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            repository: IAdventureRepository,
            errorsHandler: ErrorsHandler
    ): StartPointPresenter {
        return StartPointPresenter(
                backgroundScheduler,
                mainScheduler,
                repository,
                errorsHandler,
                adventure
        )
    }

    @Provides
    @ViewScope
    fun provideShadowGenerator(
            context: Context,
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler
    ): ShadowGenerator {
        return ShadowGenerator(context, backgroundScheduler, mainScheduler)
    }

}