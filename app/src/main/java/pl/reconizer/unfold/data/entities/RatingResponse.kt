package pl.reconizer.unfold.data.entities

import com.google.gson.annotations.SerializedName

data class RatingResponse(
        @SerializedName("user_id") val userId: String,
        @SerializedName("adventure_id") val adventureId: String,
        val rating: Int
)