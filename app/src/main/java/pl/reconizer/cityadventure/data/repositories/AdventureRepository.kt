package pl.reconizer.cityadventure.data.repositories

import io.reactivex.Single
import pl.reconizer.cityadventure.data.entities.ClueResponse
import pl.reconizer.cityadventure.data.mappers.ClueMapper
import pl.reconizer.cityadventure.data.network.api.IAdventureApi
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.entities.Clue
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository

class AdventureRepository(
    private val adventureApi: IAdventureApi,
    private val clueMapper: ClueMapper
) : IAdventureRepository {

    override fun getAdventures(lat: Double, lng: Double): Single<List<Adventure>> {
        return adventureApi.getAdventures(lat, lng)
    }

    override fun getAdventure(adventureId: String): Single<AdventureStartPoint> {
        return adventureApi.getAdventure(adventureId)
    }

    override fun getAdventureDiscoveredClues(adventureId: String): Single<List<Clue>> {
        return adventureApi.getAdventureDiscoveredClues(adventureId)
                .flatMap {
                    val testSecondId = "test-id"
                    val newCluesList = mutableListOf<ClueResponse>()
                    it.forEach {
                        newCluesList.add(it)
                        newCluesList.add(it.copy(
                                pointId = testSecondId,
                                description = it.description + testSecondId
                        ))
                    }
                    //Single.just(it.map { response -> clueMapper.map(response) })
                    Single.just(newCluesList.map { response -> clueMapper.map(response) })
                }
    }

    override fun getAdventureCompletedPoints(adventureId: String): Single<List<AdventurePoint>> {
        return adventureApi.getAdventureCompletedPoints(adventureId)
    }

}