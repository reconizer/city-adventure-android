package pl.reconizer.cityadventure.presentation.adventure.summary

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.adventure.ranking.RankingAdapter
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
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