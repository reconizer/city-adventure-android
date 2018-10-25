package pl.reconizer.cityadventure.presentation.map

import com.winterbe.expekt.expect
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.RobolectricTestRunner
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.Position

@RunWith(RobolectricTestRunner::class)
@Config(shadows= [
    ShadowBitmapDescriptorFactory::class
])
class AdventurePinMapperTest {

    private lateinit var adventure: Adventure

    private lateinit var pinMapper: AdventurePinMapper

    @Before
    fun setUp() {
        pinMapper = AdventurePinMapper()
    }

    @Test
    fun determinePin_purchasableNotStartedAndNotCompleted() {
        adventure = adventureFactory(
                purchasable = true,
                completed = false,
                started = false
        )
        expect(pinMapper.determinePin(adventure)).to.equal(pinMapper.purchasablePin)
    }

    fun adventureFactory(purchasable: Boolean, completed: Boolean, started: Boolean): Adventure {
        return Adventure(
                adventureId = "1",
                startPointId = "2",
                position = Position(0.0, 0.0),
                purchasable = purchasable,
                completed = completed,
                started = started
        )
    }

}