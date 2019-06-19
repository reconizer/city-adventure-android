package pl.reconizer.unfold.domain.entities

import com.google.gson.annotations.SerializedName

data class Adventure(
        val id: String,
        val name: String,
        @SerializedName("min_time") val minFinishTime: Long?,
        @SerializedName("max_time") val maxFinishTime: Long?,
        @SerializedName("image_url") val coverImage: String,
        @SerializedName("difficulty_level") val difficultyLevelValue: Int,
        val rating: Float
) {

    val difficultyLevel
        get() = DifficultyLevel.fromInt(difficultyLevelValue)

}