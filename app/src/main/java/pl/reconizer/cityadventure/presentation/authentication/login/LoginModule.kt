package pl.reconizer.cityadventure.presentation.authentication.login

import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository

@Module
class LoginModule {

    @Provides
    @ViewScope
    fun providePresenter(authenticationRepository: IAuthenticationRepository): LoginPresenter {
        return LoginPresenter(authenticationRepository)
    }

}