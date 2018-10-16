package pl.reconizer.cityadventure.di.modules

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.di.scopes.ActivityScope
import pl.reconizer.cityadventure.presentation.navigation.INavigator
import pl.reconizer.cityadventure.presentation.navigation.Navigator

@Module
class MainActivityModule(
        @IdRes private val fragmentContainer: Int,
        private val fragmentManager: FragmentManager
) {

    @Provides
    @ActivityScope
    fun provideNavigator(): INavigator {
        return Navigator(fragmentContainer, fragmentManager)
    }

}