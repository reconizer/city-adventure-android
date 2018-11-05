package pl.reconizer.cityadventure.domain.usecases.adventure

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository

class GetAdventuresSpek : Spek({

    describe("get adventures usecase") {
        val position = Position(2.0, 5.0)
        val scheduler = Schedulers.trampoline()
        val repository = mock<IAdventureRepository>()
        val adventures = listOf(Adventure(
                adventureId = "1",
                startPointId = "2",
                position = position,
                completed = false,
                purchasable = false,
                started = false
        ))
        val getAdventures = GetAdventures(
                scheduler,
                repository
        )

        before {
            whenever(repository.getAdventures(any(), any())).thenReturn(Single.just(adventures))
        }

        it("returns adventures") {
            val testObservable = getAdventures(position).test()
            verify(repository, atLeastOnce()).getAdventures(position.lat, position.lng)
            testObservable.assertComplete()
            testObservable.assertValue(adventures)
        }
    }

})