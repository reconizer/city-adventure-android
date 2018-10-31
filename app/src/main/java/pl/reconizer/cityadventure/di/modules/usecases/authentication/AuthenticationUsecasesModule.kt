package pl.reconizer.cityadventure.di.modules.usecases.authentication

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import pl.reconizer.cityadventure.domain.repositories.IUserRepository
import pl.reconizer.cityadventure.domain.usecases.authentication.SignIn
import javax.inject.Named

@Module
class AuthenticationUsecasesModule {

    @Provides
    @ViewScope
    fun provideSignInUsecase(
        @Named("background") scheduler: Scheduler,
        userRepository: IUserRepository,
        authenticationRepository: IAuthenticationRepository
    ): SignIn {
        return SignIn(scheduler, userRepository, authenticationRepository)
    }

}