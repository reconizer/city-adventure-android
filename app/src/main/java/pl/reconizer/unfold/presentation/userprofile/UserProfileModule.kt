package pl.reconizer.unfold.presentation.userprofile

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.userprofile.adventures.UserAdventuresAdapter
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class UserProfileModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            userRepository: IUserRepository,
            errorHandler: ErrorHandler<Error>
    ): UserProfilePresenter {
        return UserProfilePresenter(
                backgroundScheduler,
                mainScheduler,
                userRepository,
                errorHandler
        )
    }

    @Provides
    @ViewScope
    fun provideAdapter(): UserAdventuresAdapter {
        return UserAdventuresAdapter()
    }

}