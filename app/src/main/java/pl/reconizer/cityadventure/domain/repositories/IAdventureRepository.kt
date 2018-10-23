package pl.reconizer.cityadventure.domain.repositories

import io.reactivex.Single
import pl.reconizer.cityadventure.domain.entities.Adventure

interface IAdventureRepository {

    fun getAdventures(lat: Double, lng: Double): Single<List<Adventure>>

}