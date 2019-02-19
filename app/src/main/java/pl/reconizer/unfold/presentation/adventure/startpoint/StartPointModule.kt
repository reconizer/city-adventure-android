package pl.reconizer.unfold.presentation.adventure.startpoint

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.customviews.ShadowGenerator
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class StartPointModule(
        private val adventure: Adventure
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            repository: IAdventureRepository,
            errorHandler: ErrorHandler<Error>
    ): StartPointPresenter {
        return StartPointPresenter(
                backgroundScheduler,
                mainScheduler,
                repository,
                errorHandler,
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