package pl.reconizer.unfold.domain.entities.forms

import com.google.gson.annotations.SerializedName

data class UserProfileForm(
        val nick: String?,
        @SerializedName("asset_id") val avatarId: String?
) {

    fun isValid(): Boolean {
        return !nick.isNullOrBlank()
    }

}