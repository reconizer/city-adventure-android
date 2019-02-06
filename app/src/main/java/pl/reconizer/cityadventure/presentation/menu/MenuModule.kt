package pl.reconizer.cityadventure.presentation.menu

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.modules.ErrorHandlersModule
import pl.reconizer.cityadventure.di.modules.usecases.authentication.AuthenticationUsecasesModule
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.repositories.IUserRepository
import pl.reconizer.cityadventure.domain.usecases.authentication.Logout
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
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