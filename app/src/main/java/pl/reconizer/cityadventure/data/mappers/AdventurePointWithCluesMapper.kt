package pl.reconizer.cityadventure.data.mappers

import pl.reconizer.cityadventure.data.entities.AdventurePointWithCluesResponse
import pl.reconizer.cityadventure.domain.common.Mapper
import pl.reconizer.cityadventure.domain.entities.AdventurePointWithClues
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdventurePointWithCluesMapper @Inject constructor(
        private val clueMapper: ClueMapper
) : Mapper<AdventurePointWithCluesResponse, AdventurePointWithClues>() {

    override fun map(from: AdventurePointWithCluesResponse): AdventurePointWithClues {
        return AdventurePointWithClues(
                id = from.id,
                discoveryDate = Calendar.getInstance().apply {
                    timeInMillis = from.discoveryTimestamp * 1000
                }.time,
                clues = from.clues.map { clueMapper.map(it) }
        )
    }

}