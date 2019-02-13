package pl.reconizer.unfold

import android.app.Application
import pl.reconizer.unfold.di.Injector

class UnfoldApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Injector.buildAppComponent(this)
    }

}