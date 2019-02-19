package pl.reconizer.unfold.data.mappers

import com.nhaarman.mockitokotlin2.*
import com.winterbe.expekt.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.AdventurePointWithCluesResponse
import pl.reconizer.unfold.domain.entities.AdventurePointWithClues
import java.util.*

class AdventurePointWithCluesMapperSpec : Spek({

    describe("AdventurePointWithCluesMapper") {
        lateinit var pointMapper: AdventurePointWithCluesMapper
        lateinit var clueMapper: ClueMapper

        val date = Calendar.getInstance().time

        val pointResponse = AdventurePointWithCluesResponse(
                id = "test-id",
                discoveryTimestamp = date.time / 1000,
                clues = listOf(mock())
        )

        beforeEachTest {
            clueMapper = mock()
            pointMapper = AdventurePointWithCluesMapper(clueMapper)
            whenever(clueMapper.map(any())).thenReturn(mock())
        }

        it ("creates AdventurePointWithClues domain object") {
            expect(pointMapper.map(pointResponse)::class).to.equal(AdventurePointWithClues::class)
        }

        it ("correctly maps fields") {
            val point = pointMapper.map(pointResponse)
            expect(point.id).to.equal(pointResponse.id)
            expect(point.discoveryDate).to == date
            verify(clueMapper, atLeastOnce()).map(any())
        }

    }
})