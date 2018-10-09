package pl.reconizer.cityadventure.presentation.authentication.login

import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter

class LoginPresenter(
        authenticationRepository: IAuthenticationRepository
) : BasePresenter<ILoginView>() {
}