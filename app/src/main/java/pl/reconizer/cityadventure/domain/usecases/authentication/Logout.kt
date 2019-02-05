package pl.reconizer.cityadventure.domain.usecases.authentication

import io.reactivex.Completable
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository

class Logout(
        private val scheduler: Scheduler,
        private val authenticationRepository: IAuthenticationRepository
) {

    operator fun invoke(): Completable {
        return authenticationRepository.clearToken()
                .subscribeOn(scheduler)
    }

}