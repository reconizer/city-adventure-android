package pl.reconizer.unfold.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.data.mappers.TokenMapper
import pl.reconizer.unfold.data.network.api.IAuthenticationApi
import pl.reconizer.unfold.data.network.api.IUserApi
import pl.reconizer.unfold.domain.entities.CollectionContainer
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.entities.forms.UserProfileForm
import pl.reconizer.unfold.domain.repositories.IUserRepository
import kotlin.random.Random

class UserRepository(
        private val authenticationApi: IAuthenticationApi,
        private val userApi: IUserApi,
        private val tokenMapper: TokenMapper
) : IUserRepository{

    override fun login(email: String, password: String): Single<String> {
        return authenticationApi.login(email, password)
                .flatMap { tokenMapper.asyncMap(it) }
    }

    override fun register(
            email: String,
            username: String,
            password: String
    ): Single<String> {
        return Single.just("dev")
    }

    override fun sendResetPasswordCode(email: String): Completable {
        return Completable.complete()
    }

    override fun resetPassword(code: String, newPassword: String): Completable {
        return Completable.complete()
    }

    override fun getProfile(): Single<UserProfile> {
        return userApi.getProfile()
    }

    override fun updateProfile(form: UserProfileForm): Completable {
        return userApi.updateProfile(form)
    }

    override fun getCompletedAdventures(page: Int): Single<ICollectionContainer<UserAdventure>> {
        return Single.fromCallable {
            val adventures = when (page) {
                1 -> generateUserAdventures(20, "page1")
                2 -> generateUserAdventures(20, "page2")
                3 -> generateUserAdventures(20, "page3")
                4 -> generateUserAdventures(5, "page4")
                else -> emptyList()
            }
            CollectionContainer(adventures)
        }
    }

    private fun generateUserAdventures(amount: Int, prefix: String): List<UserAdventure> {
        val adventures = mutableListOf<UserAdventure>()
        for (i in 1..amount) {
            adventures.add(UserAdventure(
                    id = i.toString(),
                    name = "Test $prefix $i",
                    rating = 4.0,
                    completionTime = Random.nextLong(240, 3600 * 24 * 5),
                    position = Random.nextInt(1, 100),
                    coverImage = "https://via.placeholder.com/640x360"
            ))
        }
        return adventures
    }

}