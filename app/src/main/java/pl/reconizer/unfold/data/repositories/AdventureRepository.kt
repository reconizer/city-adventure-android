package pl.reconizer.unfold.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.data.mappers.AdventurePointWithCluesMapper
import pl.reconizer.unfold.data.mappers.AdventureStartPointMapper
import pl.reconizer.unfold.data.network.api.IAdventureApi
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleAnswerForm
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleResponse
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import java.util.*

class AdventureRepository(
    private val adventureApi: IAdventureApi,
    private val adventurePointWithCluesMapper: AdventurePointWithCluesMapper,
    private val adventureStartPointMapper: AdventureStartPointMapper
) : IAdventureRepository {

    override fun getAdventures(lat: Double, lng: Double): Single<List<MapAdventure>> {
        return adventureApi.getAdventures(lat, lng)
    }

    override fun searchAdventures(
            page: Int,
            position: Position,
            order: AdventuresSort,
            difficultyLevels: List<DifficultyLevel>,
            name: String,
            range: Float?
    ): Single<ICollectionContainer<Adventure>> {
        return adventureApi.searchAdventures(
                page = page,
                lat = position.lat,
                lng = position.lng,
                name = if (name.isBlank()) null else name,
                difficultyLevel = difficultyLevels.firstOrNull()?.value,
                range = range,
                order = order.name.toLowerCase()
        )
                .map { CollectionContainer(it) }
    }

    override fun getAdventure(adventureId: String): Single<AdventureStartPoint> {
        return adventureApi.getAdventure(adventureId)
                .flatMap { response ->
                    // TODO: remove when api is ready and i has real started_at
                    val modifiedResponse = response.copy(startedAtTimestamp = Calendar.getInstance().apply {
                        add(Calendar.SECOND, -8560)
                    }.timeInMillis / 1000)
                    adventureStartPointMapper.asyncMap(modifiedResponse)
                }
    }

    override fun getAdventureDiscoveredClues(adventureId: String): Single<List<AdventurePointWithClues>> {
        return adventureApi.getAdventureDiscoveredClues(adventureId)
                .flatMap {
                    Single.just(it.map { response -> adventurePointWithCluesMapper.map(response) }.sortedByDescending { it.discoveryDate })
                }
    }

    override fun getAdventureCompletedPoints(adventureId: String): Single<List<AdventurePoint>> {
        return adventureApi.getAdventureCompletedPoints(adventureId)
    }

    override fun startAdventure(adventureId: String): Completable {
        return adventureApi.startAdventure(adventureId)
    }

    override fun resolvePoint(form: PuzzleAnswerForm): Single<PuzzleResponse> {
        return adventureApi.resolvePoint(form)
    }

    override fun userAdventureRanking(adventureId: String): Single<RankingEntry> {
        return adventureApi.userAdventureRanking(adventureId)
    }

    override fun getSummary(adventureId: String): Single<List<RankingEntry>> {
        return adventureApi.getSummary(adventureId)
    }

    override fun rate(adventureId: String, rating: Int): Completable {
        return adventureApi.rate(adventureId, rating).ignoreElement()
    }

}