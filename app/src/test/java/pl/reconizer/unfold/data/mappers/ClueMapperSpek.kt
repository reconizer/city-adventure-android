package pl.reconizer.unfold.data.mappers

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.winterbe.expekt.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.ClueResponse
import pl.reconizer.unfold.data.entities.VideoResourceResponse
import pl.reconizer.unfold.domain.entities.Clue
import pl.reconizer.unfold.domain.entities.ClueType

class ClueMapperSpek : Spek({

    describe("ClueMapper") {
        lateinit var clueMapper: ClueMapper
        lateinit var videoResourceMapper: VideoResourceMapper

        var videoResource = mock<VideoResourceResponse>()

        var textClue = ClueResponse(
                id = "test-id",
                originalResourceUrl = null,
                type = "text",
                description = "text",
                videoResources = null
        )
        var videoClue = textClue.copy(
                originalResourceUrl = "video-url",
                type = "video",
                videoResources = listOf(videoResource)
        )

        beforeEachTest {
            videoResourceMapper = mock()
            clueMapper = ClueMapper(videoResourceMapper)
        }

        it ("creates Clue domain object") {
            expect(clueMapper.map(textClue)::class).to.equal(Clue::class)
        }

        it ("correctly maps fields") {
            val mappedClue = clueMapper.map(textClue)
            expect(mappedClue.id).to.equal(textClue.id)
            expect(mappedClue.originalResourceUrl).to.be.`null`
            expect(mappedClue.description).to.equal(textClue.description)
        }

        context("mapping text clue") {

            it ("correctly maps a clue's type") {
                expect(clueMapper.map(textClue).type).to.equal(ClueType.TEXT)
            }

        }

        context("mapping video clue") {

            it ("correctly maps a clue's type") {
                expect(clueMapper.map(videoClue).type).to.equal(ClueType.VIDEO)
            }

            it ("maps video resources") {
                clueMapper.map(videoClue)
                verify(videoResourceMapper, atLeastOnce()).map(any())
            }

        }
    }
})