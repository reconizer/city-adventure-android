package pl.reconizer.cityadventure.domain.usecases.authentication

import io.reactivex.Completable
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import pl.reconizer.cityadventure.domain.repositories.IUserRepository

class SignIn(
        private val scheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val authenticationRepository: IAuthenticationRepository
) {

    operator fun invoke(email: String, password: String): Completable {
        return userRepository.login(email, password)
                .flatMapCompletable { authenticationRepository.saveToken(it) }
                .subscribeOn(scheduler)
    }

}