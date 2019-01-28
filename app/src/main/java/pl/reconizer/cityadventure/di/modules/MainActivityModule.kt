package pl.reconizer.cityadventure.di.modules

import android.content.Context
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.BackstackDelegate
import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.di.scopes.ActivityScope
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.location.LocationProvider
import pl.reconizer.cityadventure.presentation.navigation.FragmentStateChanger
import pl.reconizer.cityadventure.presentation.navigation.INavigator
import pl.reconizer.cityadventure.presentation.navigation.Navigator

@Module
class MainActivityModule(
        @IdRes private val fragmentContainer: Int,
        private val activity: AppCompatActivity
) {

    @Provides
    @ActivityScope
    fun provideLocationProvider(context: Context): ILocationProvider {
        return LocationProvider(context)
    }

    @Provides
    @ActivityScope
    fun provideNavigator(): INavigator {
        return Navigator(fragmentContainer, activity.supportFragmentManager)
    }

    @Provides
    @ActivityScope
    fun provideBackstackDelegate(): BackstackDelegate {
        return BackstackDelegate()
    }

    @Provides
    @ActivityScope
    fun provideFragmentStateChanger(): FragmentStateChanger {
        return FragmentStateChanger(activity.supportFragmentManager, fragmentContainer)
    }

}