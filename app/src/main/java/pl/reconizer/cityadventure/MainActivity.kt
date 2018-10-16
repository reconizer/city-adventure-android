package pl.reconizer.cityadventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.reconizer.cityadventure.di.modules.MainActivityModule
import pl.reconizer.cityadventure.presentation.navigation.INavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: INavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CityAdventureApp.appComponent.activityComponent(MainActivityModule(R.id.fragmentContainer, supportFragmentManager))
    }
}
