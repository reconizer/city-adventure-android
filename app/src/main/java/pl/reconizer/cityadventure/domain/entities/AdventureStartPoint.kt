package pl.reconizer.cityadventure.domain.entities

import com.google.gson.annotations.SerializedName

data class AdventureStartPoint(
        val id: String,
        val description: String,
        val name: String,
        @SerializedName("difficulty_level") val difficultyLevelValue: Int,
        @SerializedName("image_url") val coverImage: String,
        val gallery: List<String>,
        val language: String,
        val rating: Double?,
        @SerializedName("rating_count") val ratingCount: Int,
        @SerializedName("author_name") val authorName: String,
        @SerializedName("author_image_url") val authorImage: String,
        @SerializedName("min_time") val minFinishTime: Long?,
        @SerializedName("max_time") val maxFinishTime: Long?,
        @SerializedName("current_user_ranking") val currentUserRanking: RankingEntry?,
        @SerializedName("top_five") val topFiveRanking: List<RankingEntry>,
        @SerializedName("current_user_rating") val currentUserRating: Int?

) {

    val difficultyLevel
            get() = DifficultyLevel.fromInt(difficultyLevelValue)

}