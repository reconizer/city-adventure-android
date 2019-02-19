package pl.reconizer.unfold.di.modules.usecases.authentication

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IAuthenticationRepository
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.domain.usecases.authentication.Logout
import pl.reconizer.unfold.domain.usecases.authentication.SignIn
import pl.reconizer.unfold.domain.usecases.authentication.SignUp
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

    @Provides
    @ViewScope
    fun provideSignUpUsecase(
            @Named("background") scheduler: Scheduler,
            userRepository: IUserRepository,
            authenticationRepository: IAuthenticationRepository
    ): SignUp {
        return SignUp(scheduler, userRepository, authenticationRepository)
    }

    @Provides
    @ViewScope
    fun provideLogoutUsecase(
            @Named("background") scheduler: Scheduler,
            authenticationRepository: IAuthenticationRepository
    ): Logout {
        return Logout(scheduler, authenticationRepository)
    }

}