package pl.reconizer.cityadventure.data.repositories

import io.reactivex.Single
import pl.reconizer.cityadventure.data.network.api.IAdventureApi
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository

class AdventureRepository(
        private val adventureApi: IAdventureApi
) : IAdventureRepository {

    override fun getAdventures(lat: Double, lng: Double): Single<List<Adventure>> {
        return adventureApi.getAdventures(lat, lng)
//        return Single.just(listOf(
//                Adventure(
//                        adventureId = "1",
//                        startPointId = "1",
//                        position = Position(53.010612, 18.608255),
//                        started = false,
//                        purchasable = false,
//                        completed = false,
//                        purchased = false
//                ),
//                Adventure(
//                        adventureId = "2",
//                        startPointId = "2",
//                        position = Position(53.010989, 18.607530),
//                        started = false,
//                        purchasable = true,
//                        completed = false,
//                        purchased = true
//                ),
//                Adventure(
//                        adventureId = "3",
//                        startPointId = "3",
//                        position = Position(53.010591, 18.607667),
//                        started = true,
//                        purchasable = false,
//                        completed = false,
//                        purchased = false
//                ),
//                Adventure(
//                        adventureId = "4",
//                        startPointId = "4",
//                        position = Position(53.010276, 18.607966),
//                        started = false,
//                        purchasable = false,
//                        completed = true,
//                        purchased = false
//                ),
//                Adventure(
//                        adventureId = "5",
//                        startPointId = "5",
//                        position = Position(53.010191, 18.607667),
//                        started = true,
//                        purchasable = true,
//                        completed = false,
//                        purchased = true
//                ),
//                Adventure(
//                        adventureId = "6",
//                        startPointId = "6",
//                        position = Position(53.010176, 18.607966),
//                        started = false,
//                        purchasable = true,
//                        completed = true,
//                        purchased = true
//                )
//        ))
    }

    override fun getAdventure(adventureId: String): Single<AdventureStartPoint> {
        return adventureApi.getAdventure(adventureId)
    }

}