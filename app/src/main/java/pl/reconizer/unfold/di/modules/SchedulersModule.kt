package pl.reconizer.unfold.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulersModule {

    @Provides
    @Named("background")
    @Singleton
    fun provideBackgroundScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named("main")
    @Singleton
    fun provideMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}