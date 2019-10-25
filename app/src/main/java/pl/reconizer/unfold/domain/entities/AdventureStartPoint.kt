package pl.reconizer.unfold.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class AdventureStartPoint(
        val id: String,
        val description: String,
        val name: String,
        val difficultyLevel: DifficultyLevel?,
        val coverImage: String,
        val gallery: List<String>,
        val language: String,
        val rating: Double?,
        val ratingCount: Int,
        val authorId: String,
        val authorName: String,
        val authorImage: String,
        val minFinishTime: Long?,
        val maxFinishTime: Long?,
        val currentUserRanking: RankingEntry?,
        val topFiveRanking: List<RankingEntry>,
        val currentUserRating: Int?,
        val startedAt: Date?
): Parcelable