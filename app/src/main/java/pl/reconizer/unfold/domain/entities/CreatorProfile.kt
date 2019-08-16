package pl.reconizer.unfold.domain.entities

import com.google.gson.annotations.SerializedName

data class CreatorProfile(
        val id: String,
        val name: String,
        val description: String,
        @SerializedName("followers_count") val followersCount: Int,
        @SerializedName("image_url") val logo: String?
)