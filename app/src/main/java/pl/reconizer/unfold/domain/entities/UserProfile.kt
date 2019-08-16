package pl.reconizer.unfold.domain.entities

import com.google.gson.annotations.SerializedName

data class UserProfile(
        val id: String,
        val nick: String,
        val email: String,
        @SerializedName("avatar_url") val avatarUrl: String
)