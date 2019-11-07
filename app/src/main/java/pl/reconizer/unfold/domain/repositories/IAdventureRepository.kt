package pl.reconizer.unfold.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleAnswerForm
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleResponse

interface IAdventureRepository {

    fun getAdventures(lat: Double, lng: Double): Single<List<MapAdventure>>

    fun searchAdventures(
            page: Int,
            position: Position,
            order: AdventuresSort,
            difficultyLevels: List<DifficultyLevel>,
            name: String,
            range: Float?
    ): Single<ICollectionContainer<Adventure>>

    fun getAdventure(adventureId: String): Single<AdventureStartPoint>

    fun getAdventureDiscoveredClues(adventureId: String): Single<List<AdventurePointWithClues>>

    fun getAdventureCompletedPoints(adventureId: String): Single<List<AdventurePoint>>

    fun startAdventure(adventureId: String): Completable

    fun resolvePoint(form: PuzzleAnswerForm): Single<PuzzleResponse>

    fun userAdventureRanking(adventureId: String): Single<RankingEntry>

    fun getSummary(adventureId: String): Single<List<RankingEntry>>

    fun rate(adventureId: String, rating: Int): Completable

}