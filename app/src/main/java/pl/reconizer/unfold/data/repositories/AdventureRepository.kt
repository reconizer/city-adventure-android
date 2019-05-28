package pl.reconizer.unfold.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.data.mappers.AdventurePointWithCluesMapper
import pl.reconizer.unfold.data.network.api.IAdventureApi
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.repositories.IAdventureRepository

class AdventureRepository(
    private val adventureApi: IAdventureApi,
    private val adventurePointWithCluesMapper: AdventurePointWithCluesMapper
) : IAdventureRepository {

    override fun getAdventures(lat: Double, lng: Double): Single<List<Adventure>> {
        return adventureApi.getAdventures(lat, lng)
    }

    override fun getAdventure(adventureId: String): Single<AdventureStartPoint> {
        return adventureApi.getAdventure(adventureId)
    }

    override fun getAdventureDiscoveredClues(adventureId: String): Single<List<AdventurePointWithClues>> {
        return adventureApi.getAdventureDiscoveredClues(adventureId)
                .flatMap {
                    Single.just(it.map { response -> adventurePointWithCluesMapper.map(response) }.asReversed())
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