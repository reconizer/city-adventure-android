package pl.reconizer.cityadventure.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.reconizer.cityadventure.BuildConfig
import pl.reconizer.cityadventure.data.network.interceptors.AuthenticationInterceptor
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Named("logging")
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Named("authentication")
    @Singleton
    fun provideAuthenticationInterceptor(repository: IAuthenticationRepository): Interceptor {
        return AuthenticationInterceptor(repository)
    }

    @Provides
    @Singleton
    fun provideOkhttp(
            @Named("logging") loggingInterceptor: Interceptor,
            @Named("authentication") authenticationInterceptor: Interceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(loggingInterceptor)
        }
        httpClient.addInterceptor(authenticationInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
            okHttp: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttp)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        const val BASE_API_URL = "http://192.168.1.104:4008"
    }


}