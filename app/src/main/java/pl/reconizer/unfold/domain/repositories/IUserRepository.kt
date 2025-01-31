package pl.reconizer.unfold.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.Avatar
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.entities.forms.UserProfileForm

interface IUserRepository {

    fun login(email: String, password: String): Single<String>

    fun register(
            email: String,
            username: String,
            password: String
    ): Single<String>

    fun sendResetPasswordCode(email: String): Completable

    fun resetPassword(code: String, newPassword: String): Completable

    fun getProfile(): Single<UserProfile>

    fun updateProfile(form: UserProfileForm): Completable

    fun updateAvatar(avatarId: String): Completable

    fun getAvatars(): Single<List<Avatar>>

    fun getCompletedAdventures(page: Int = 1): Single<ICollectionContainer<UserAdventure>>

    fun getStartedAdventures(page: Int = 1): Single<ICollectionContainer<UserAdventure>>

    fun getPurchasedAdventures(page: Int = 1): Single<ICollectionContainer<UserAdventure>>

    fun getStartedAdventuresCount(): Single<Int>

}