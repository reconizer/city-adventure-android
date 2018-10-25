package pl.reconizer.cityadventure.di.modules.usecases

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventures
import javax.inject.Named

@Module
class AdventureUsecasesModule {

    @Provides
    @ViewScope
    fun provideGetAdventuresUsecase(
            @Named("background") scheduler: Scheduler,
            repository: IAdventureRepository
    ): GetAdventures {
        return GetAdventures(scheduler, repository)
    }

}