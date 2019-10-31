package pl.reconizer.unfold.presentation.userprofile

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.userprofile.adventures.UserAdventuresAdapter
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
])
class UserProfileModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            userRepository: IUserRepository,
            errorsHandler: ErrorsHandler
    ): UserProfilePresenter {
        return UserProfilePresenter(
                backgroundScheduler,
                mainScheduler,
                userRepository,
                errorsHandler
        )
    }

    @Provides
    @ViewScope
    fun provideAdapter(): UserAdventuresAdapter {
        return UserAdventuresAdapter()
    }

}