package pl.reconizer.cityadventure.domain.usecases.adventure

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository

class GetAdventureStartPointSpek : Spek({

    describe("get adventure's start point usecase") {
        val repository = mock<IAdventureRepository>()
        val adventure = mock<AdventureStartPoint>()
        val getAdventureStartPoint = GetAdventureStartPoint(
                repository
        )

        before {
            whenever(repository.getAdventure(any())).thenReturn(Single.just(adventure))
        }

        it("returns adventure") {
            val id = "test-id"
            val testObservable = getAdventureStartPoint(id).test()
            verify(repository, atLeastOnce()).getAdventure(id)
            testObservable.assertComplete()
            testObservable.assertValue(adventure)
        }
    }

})