package pl.reconizer.unfold.di.modules

import dagger.Module
import dagger.Provides
import pl.reconizer.unfold.data.network.api.IAdventureApi
import pl.reconizer.unfold.data.network.api.IAuthenticationApi
import pl.reconizer.unfold.data.network.api.IUserApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): IAuthenticationApi {
        return retrofit.create(IAuthenticationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAdventureApi(retrofit: Retrofit): IAdventureApi {
        return retrofit.create(IAdventureApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): IUserApi {
        return retrofit.create(IUserApi::class.java)
    }

}