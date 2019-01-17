package pl.reconizer.cityadventure.di.modules.usecases

import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventureStartPoint

@Module
class AdventureUsecasesModule {

    @Provides
    @ViewScope
    fun provideGetAdventureStartPointUsecase(
            repository: IAdventureRepository
    ): GetAdventureStartPoint {
        return GetAdventureStartPoint(repository)
    }

}