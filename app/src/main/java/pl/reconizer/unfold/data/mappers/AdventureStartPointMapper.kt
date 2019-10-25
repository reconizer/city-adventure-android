package pl.reconizer.unfold.data.mappers

import pl.reconizer.unfold.data.entities.AdventureStartPointResponse
import pl.reconizer.unfold.domain.common.Mapper
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.entities.DifficultyLevel
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdventureStartPointMapper @Inject constructor() : Mapper<AdventureStartPointResponse, AdventureStartPoint>() {

    override fun map(from: AdventureStartPointResponse): AdventureStartPoint {
        return AdventureStartPoint(
                id = from.id,
                description = from.description ?: "",
                name = from.name,
                difficultyLevel = if (from.difficultyLevelValue == null) null else DifficultyLevel.fromInt(from.difficultyLevelValue),
                coverImage = from.coverImage,
                gallery = from.gallery,
                language = from.language ?: "PL",
                rating = from.rating,
                ratingCount = from.ratingCount,
                authorId = from.authorId,
                authorName = from.authorName,
                authorImage = from.authorImage,
                minFinishTime = from.minFinishTime,
                maxFinishTime = from.maxFinishTime,
                currentUserRanking = from.currentUserRanking,
                topFiveRanking = from.topFiveRanking,
                currentUserRating = from.currentUserRating,
                startedAt = if (from.startedAtTimestamp == null) {
                    null
                } else {
                    Calendar.getInstance().apply {
                        timeInMillis = from.startedAtTimestamp * 1000
                    }.time
                }
        )
    }

}