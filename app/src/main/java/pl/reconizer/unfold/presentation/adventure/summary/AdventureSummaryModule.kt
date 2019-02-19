package pl.reconizer.unfold.presentation.adventure.summary

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.adventure.ranking.RankingAdapter
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class AdventureSummaryModule(
        private val adventure: Adventure
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            adventureRepository: IAdventureRepository,
            errorHandler: ErrorHandler<Error>
    ): AdventureSummaryPresenter {
        return AdventureSummaryPresenter(
                backgroundScheduler,
                mainScheduler,
                adventureRepository,
                errorHandler,
                adventure
        )
    }

    @Provides
    @ViewScope
    fun provideRankingAdapter(): RankingAdapter {
        return RankingAdapter()
    }

}