package pl.reconizer.unfold.domain.usecases.authentication

import io.reactivex.Completable
import io.reactivex.Scheduler
import pl.reconizer.unfold.domain.repositories.IAuthenticationRepository
import pl.reconizer.unfold.domain.repositories.IUserRepository

class SignUp(
        private val scheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val authenticationRepository: IAuthenticationRepository
) {

    operator fun invoke(
            email: String,
            username: String,
            password: String): Completable {
        return userRepository.register(email, username, password)
                .flatMapCompletable { authenticationRepository.saveToken(it) }
                .subscribeOn(scheduler)
    }

}