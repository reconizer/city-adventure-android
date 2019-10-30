package pl.reconizer.unfold

import android.app.Application
import pl.reconizer.unfold.di.Injector
import timber.log.Timber

class UnfoldApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Injector.buildAppComponent(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}