package pl.reconizer.unfold.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.reconizer.unfold.data.local.ILocalStorage
import pl.reconizer.unfold.data.local.SharedPreferencesStorage
import pl.reconizer.unfold.data.mappers.AdventurePointWithCluesMapper
import pl.reconizer.unfold.data.mappers.AdventureStartPointMapper
import pl.reconizer.unfold.data.mappers.TokenMapper
import pl.reconizer.unfold.data.network.api.IAdventureApi
import pl.reconizer.unfold.data.network.api.IAuthenticationApi
import pl.reconizer.unfold.data.network.api.ICreatorApi
import pl.reconizer.unfold.data.network.api.IUserApi
import pl.reconizer.unfold.data.repositories.AdventureRepository
import pl.reconizer.unfold.data.repositories.AuthenticationRepository
import pl.reconizer.unfold.data.repositories.CreatorRepository
import pl.reconizer.unfold.data.repositories.UserRepository
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.domain.repositories.IAuthenticationRepository
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.domain.repositories.IUserRepository
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
    fun provideUserRepository(
            authenticationApi: IAuthenticationApi,
            userApi: IUserApi,
            adventureApi: IAdventureApi,
            tokenMapper: TokenMapper
    ): IUserRepository {
        return UserRepository(
                authenticationApi,
                userApi,
                adventureApi,
                tokenMapper
        )
    }

    @Provides
    @Singleton
    fun provideAdventureRepository(
            adventureApi: IAdventureApi,
            adventurePointWithCluesMapper: AdventurePointWithCluesMapper,
            adventureStartPointMapper: AdventureStartPointMapper
    ): IAdventureRepository {
        return AdventureRepository(
                adventureApi,
                adventurePointWithCluesMapper,
                adventureStartPointMapper
        )
    }

    @Provides
    @Singleton
    fun provideCreatorRepository(
            creatorApi: ICreatorApi,
            adventureApi: IAdventureApi
    ): ICreatorRepository {
        return CreatorRepository(creatorApi, adventureApi)
    }

}