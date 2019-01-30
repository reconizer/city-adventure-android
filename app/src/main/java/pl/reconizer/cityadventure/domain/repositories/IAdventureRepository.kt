package pl.reconizer.cityadventure.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.cityadventure.domain.entities.*

interface IAdventureRepository {

    fun getAdventures(lat: Double, lng: Double): Single<List<Adventure>>

    fun getAdventure(adventureId: String): Single<AdventureStartPoint>

    fun getAdventureDiscoveredClues(adventureId: String): Single<List<AdventurePointWithClues>>

    fun getAdventureCompletedPoints(adventureId: String): Single<List<AdventurePoint>>

    fun startAdventure(adventureId: String): Completable

    fun resolvePoint(form: PuzzleAnswerForm): Single<PuzzleResponse>

    fun userAdventureRanking(adventureId: String): Single<RankingEntry>

    fun getSummary(adventureId: String): Single<List<RankingEntry>>

}