package pl.reconizer.unfold.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.data.mappers.TokenMapper
import pl.reconizer.unfold.data.network.api.IAdventureApi
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
        private val adventureApi: IAdventureApi,
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
        return adventureApi.getUserAdventures(
                page = page,
                completed = true,
                paid = false
        )
                .map { CollectionContainer(it) }
    }

    override fun getStartedAdventures(page: Int): Single<ICollectionContainer<UserAdventure>> {
        return adventureApi.getUserAdventures(
                page = page,
                completed = false,
                paid = false
        )
                .map { CollectionContainer(it) }
    }

    override fun getPurchasedAdventures(page: Int): Single<ICollectionContainer<UserAdventure>> {
        return adventureApi.getUserAdventures(
                page = page,
                completed = false,
                paid = true
        )
                .map { CollectionContainer(it) }
    }

}