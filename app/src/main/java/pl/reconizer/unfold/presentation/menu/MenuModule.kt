package pl.reconizer.unfold.presentation.menu

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.modules.usecases.authentication.AuthenticationUsecasesModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.domain.usecases.authentication.Logout
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import javax.inject.Named

@Module(includes = [
    AuthenticationUsecasesModule::class,
    ErrorHandlersModule::class
])
class MenuModule {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            userRepository: IUserRepository,
            logout: Logout,
            errorHandler: ErrorHandler<Error>
    ): MenuPresenter {
        return MenuPresenter(
                backgroundScheduler,
                mainScheduler,
                userRepository,
                logout,
                errorHandler
        )
    }

}