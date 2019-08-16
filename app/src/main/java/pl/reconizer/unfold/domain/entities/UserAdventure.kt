package pl.reconizer.unfold.domain.entities

import com.google.gson.annotations.SerializedName

data class UserAdventure(
        val id: String,
        val name: String,
        @SerializedName("completion_time") val completionTime: Long?,
        val position: Int?,
        @SerializedName("image_url") val coverImage: String
)