package pl.reconizer.cityadventure.di.modules.usecases

import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventureStartPoint
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventures

@Module
class AdventureUsecasesModule {

    @Provides
    @ViewScope
    fun provideGetAdventuresUsecase(
            repository: IAdventureRepository
    ): GetAdventures {
        return GetAdventures(repository)
    }

    @Provides
    @ViewScope
    fun provideGetAdventureStartPointUsecase(
            repository: IAdventureRepository
    ): GetAdventureStartPoint {
        return GetAdventureStartPoint(repository)
    }

}