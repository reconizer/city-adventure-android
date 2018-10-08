package pl.reconizer.cityadventure

import android.app.Application
import pl.reconizer.cityadventure.di.components.AppComponent
import pl.reconizer.cityadventure.di.components.DaggerAppComponent
import pl.reconizer.cityadventure.di.modules.ContextModule

class CityAdventureApp : Application(){

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

}