package pl.reconizer.cityadventure.di.modules

import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.data.network.api.IAuthenticationApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): IAuthenticationApi {
        return retrofit.create(IAuthenticationApi::class.java)
    }

}