package pl.reconizer.unfold.di.modules

import android.content.Context
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.zhuinden.simplestack.BackstackDelegate
import dagger.Module
import dagger.Provides
import pl.reconizer.unfold.di.scopes.ActivityScope
import pl.reconizer.unfold.presentation.location.ILocationProvider
import pl.reconizer.unfold.presentation.location.LocationProvider
import pl.reconizer.unfold.presentation.navigation.FragmentStateChanger
import pl.reconizer.unfold.presentation.navigation.INavigator
import pl.reconizer.unfold.presentation.navigation.Navigator

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