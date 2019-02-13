package pl.reconizer.unfold.di.modules

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val appContext: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = appContext

    @Provides
    @Singleton
    fun provideResources(): Resources = appContext.resources

}