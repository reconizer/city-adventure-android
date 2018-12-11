package pl.reconizer.cityadventure.data.mappers

import com.winterbe.expekt.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.ClueResponse
import pl.reconizer.cityadventure.domain.entities.Clue
import pl.reconizer.cityadventure.domain.entities.ClueType

class ClueMapperSpek : Spek({

    describe("ClueMapper") {
        var textClue = ClueResponse(
                id = "test-id",
                originalResourceUrl = null,
                type = "text",
                description = "text",
                pointId = "some-point-id"
        )
        var videoClue = textClue.copy(
                originalResourceUrl = "video-url",
                type = "video"
        )

        it ("creates Clue domain object") {
            expect(ClueMapper().map(textClue)::class).to.equal(Clue::class)
        }

        it ("correctly maps fields") {
            val mappedClue = ClueMapper().map(textClue)
            expect(mappedClue.id).to.equal(textClue.id)
            expect(mappedClue.originalResourceUrl).to.be.`null`
            expect(mappedClue.description).to.equal(textClue.description)
            expect(mappedClue.pointId).to.equal(textClue.pointId)
        }

        context("mapping text clue") {

            it ("correctly maps a clue's type") {
                expect(ClueMapper().map(textClue).type).to.equal(ClueType.TEXT)
            }

        }

        context("mapping video clue") {

            it ("correctly maps a clue's type") {
                expect(ClueMapper().map(videoClue).type).to.equal(ClueType.VIDEO)
            }

        }
    }
})