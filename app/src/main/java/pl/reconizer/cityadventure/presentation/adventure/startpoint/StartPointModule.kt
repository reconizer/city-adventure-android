package pl.reconizer.cityadventure.presentation.adventure.startpoint

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.modules.usecases.AdventureUsecasesModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventureStartPoint
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    AdventureUsecasesModule::class,
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
            getAdventureStartPoint: GetAdventureStartPoint,
            errorHandler: ErrorHandler<Error>
    ): StartPointPresenter {
        return StartPointPresenter(
                backgroundScheduler,
                mainScheduler,
                getAdventureStartPoint,
                errorHandler,
                adventure
        )
    }

}