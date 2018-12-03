package pl.reconizer.cityadventure.data.repositories

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.network.api.IAdventureApi
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.entities.Position

class AdventureRepositorySpec : Spek({

    describe("AdventureRepository") {
        val api = mock<IAdventureApi>()
        val repository = AdventureRepository(api)

        describe("getAdventures") {
            val adventures = listOf(Adventure(
                    adventureId = "test-id",
                    startPointId = "test-point-id",
                    position = Position(0.0, 0.0),
                    completed = false,
                    purchasable = false,
                    started = false,
                    purchased = false
            ))

            before { whenever(api.getAdventures(any(), any())).thenReturn(Single.just(adventures)) }

            it("performs correct api call") {
                val lat = 1.0
                val lng = 2.0
                val testObservable = repository.getAdventures(lat, lng).test()
                verify(api, atLeastOnce()).getAdventures(lat, lng)
                testObservable.assertValue(adventures)
                testObservable.assertComplete()
            }

        }

        describe("getAdventure") {
            val adventureId = "test-id"
            val adventure = mock<AdventureStartPoint>()

            before { whenever(api.getAdventure(adventureId)).thenReturn(Single.just(adventure)) }

            it("performs correct api call") {
                val testObservable = repository.getAdventure(adventureId).test()
                verify(api, atLeastOnce()).getAdventure(adventureId)
                testObservable.assertValue(adventure)
                testObservable.assertComplete()
            }
        }

    }

})