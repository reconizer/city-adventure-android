package pl.reconizer.unfold.data.mappers

import pl.reconizer.unfold.data.entities.ClueResponse
import pl.reconizer.unfold.domain.common.Mapper
import pl.reconizer.unfold.domain.entities.Clue
import pl.reconizer.unfold.domain.entities.ClueType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClueMapper @Inject constructor(
        private val videoResourceMapper: VideoResourceMapper
) : Mapper<ClueResponse, Clue>() {

    override fun map(from: ClueResponse): Clue {
        return Clue(
                id = from.id,
                originalResourceUrl = from.originalResourceUrl,
                type = ClueType.fromName(from.type),
                description = from.description,
                videoResources = from.videoResources?.map { videoResourceMapper.map(it) }
        )
    }

}