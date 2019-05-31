package pl.reconizer.unfold.presentation.userprofile.edit

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class EditUserProfileModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            userRepository: IUserRepository,
            errorHandler: ErrorHandler<Error>
    ): EditUserProfilePresenter {
        return EditUserProfilePresenter(
                backgroundScheduler,
                mainScheduler,
                userRepository,
                errorHandler
        )
    }

}