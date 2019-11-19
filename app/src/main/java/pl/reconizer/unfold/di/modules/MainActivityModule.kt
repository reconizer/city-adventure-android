package pl.reconizer.unfold.di.modules

import android.content.Context
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.zhuinden.simplestack.BackstackDelegate
import dagger.Module
import dagger.Provides
import pl.reconizer.unfold.BuildConfig
import pl.reconizer.unfold.di.scopes.ActivityScope
import pl.reconizer.unfold.presentation.location.DebugLocationProvider
import pl.reconizer.unfold.presentation.location.ILocationProvider
import pl.reconizer.unfold.presentation.location.LocationProvider
import pl.reconizer.unfold.presentation.navigation.FragmentStateChanger

@Module
class MainActivityModule(
        @IdRes private val fragmentContainer: Int,
        private val activity: AppCompatActivity
) {

    @Provides
    @ActivityScope
    fun provideLocationProvider(context: Context): ILocationProvider {
        return if (BuildConfig.BUILD_TYPE.contentEquals("debug") || BuildConfig.BUILD_TYPE.contentEquals("internalTests")) {
            DebugLocationProvider(context)
        } else {
            LocationProvider(context)
        }
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