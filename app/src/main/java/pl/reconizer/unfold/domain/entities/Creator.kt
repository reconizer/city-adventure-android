package pl.reconizer.unfold.domain.entities

import com.google.gson.annotations.SerializedName

data class Creator(
        val id: String,
        val name: String,
        @SerializedName("image_url") val logo: String?,
        @SerializedName("follow") val isFollowed: Boolean,
        @SerializedName("followers_count") val followersCount: Int,
        @SerializedName("adventures_count") val adventuresCount: Int
)