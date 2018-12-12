package pl.reconizer.cityadventure.data.mappers

import pl.reconizer.cityadventure.data.entities.ClueResponse
import pl.reconizer.cityadventure.domain.common.Mapper
import pl.reconizer.cityadventure.domain.entities.Clue
import pl.reconizer.cityadventure.domain.entities.ClueType
import javax.inject.Inject

class ClueMapper @Inject constructor() : Mapper<ClueResponse, Clue>() {

    override fun map(from: ClueResponse): Clue {
        return Clue(
                id = from.id,
                originalResourceUrl = from.originalResourceUrl,
                type = ClueType.fromName(from.type),
                description = from.description,
                pointId = from.pointId
        )
    }

}