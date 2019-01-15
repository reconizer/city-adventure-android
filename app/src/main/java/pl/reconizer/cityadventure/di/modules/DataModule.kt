package pl.reconizer.cityadventure.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.data.local.ILocalStorage
import pl.reconizer.cityadventure.data.local.SharedPreferencesStorage
import pl.reconizer.cityadventure.data.mappers.AdventurePointWithCluesMapper
import pl.reconizer.cityadventure.data.mappers.ClueMapper
import pl.reconizer.cityadventure.data.network.api.IAdventureApi
import pl.reconizer.cityadventure.data.network.api.IAuthenticationApi
import pl.reconizer.cityadventure.data.repositories.AdventureRepository
import pl.reconizer.cityadventure.data.repositories.AuthenticationRepository
import pl.reconizer.cityadventure.data.repositories.UserRepository
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import pl.reconizer.cityadventure.domain.repositories.IUserRepository
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideLocalStorage(context: Context): ILocalStorage {
        return SharedPreferencesStorage(context)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(localStorage: ILocalStorage): IAuthenticationRepository {
        return AuthenticationRepository(localStorage)
    }

    @Provides
    @Singleton
    fun provideUserRepository(authenticationApi: IAuthenticationApi): IUserRepository {
        return UserRepository(authenticationApi)
    }

    @Provides
    @Singleton
    fun provideAdventureRepository(
            adventureApi: IAdventureApi,
            adventurePointWithCluesMapper: AdventurePointWithCluesMapper
    ): IAdventureRepository {
        return AdventureRepository(adventureApi, adventurePointWithCluesMapper)
    }

}