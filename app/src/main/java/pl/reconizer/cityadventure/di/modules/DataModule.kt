package pl.reconizer.cityadventure.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.data.local.ILocalStorage
import pl.reconizer.cityadventure.data.local.SharedPreferencesStorage
import pl.reconizer.cityadventure.data.repositories.AuthenticationRepository
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
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

}