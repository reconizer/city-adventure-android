package pl.reconizer.unfold.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.RankingEntry

@Parcelize
data class AdventureStartPointResponse(
        val id: String,
        val description: String?,
        val name: String,
        @SerializedName("difficulty_level") val difficultyLevelValue: Int?,
        @SerializedName("image_url") val coverImage: String,
        val gallery: List<String>,
        val language: String?,
        val rating: Double?,
        @SerializedName("rating_count") val ratingCount: Int,
        @SerializedName("author_id") val authorId: String,
        @SerializedName("author_name") val authorName: String,
        @SerializedName("author_image_url") val authorImage: String,
        @SerializedName("min_time") val minFinishTime: Long?,
        @SerializedName("max_time") val maxFinishTime: Long?,
        @SerializedName("current_user_ranking") val currentUserRanking: RankingEntry?,
        @SerializedName("top_five") val topFiveRanking: List<RankingEntry>,
        @SerializedName("current_user_rating") val currentUserRating: Int?,
        @SerializedName("started_at") val startedAtTimestamp: Long?
): Parcelable