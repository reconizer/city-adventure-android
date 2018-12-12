package pl.reconizer.cityadventure.domain.repositories

import io.reactivex.Single
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.entities.Clue

interface IAdventureRepository {

    fun getAdventures(lat: Double, lng: Double): Single<List<Adventure>>

    fun getAdventure(adventureId: String): Single<AdventureStartPoint>

    fun getAdventureDiscoveredClues(adventureId: String): Single<List<Clue>>

}