package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.winterbe.expekt.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.Position

class AdventurePinMapperSpek : Spek({

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

    describe("AdventurePinMapper") {
        var pinMapper = spy(AdventurePinMapper())

//        before {
//            whenever(pinMapper.purchasablePin).thenReturn(mock())
//            whenever(pinMapper.purchasableStartedPin).thenReturn(mock())
//            whenever(pinMapper.purchasableFinishedPin).thenReturn(mock())
//
//            whenever(pinMapper.freePin).thenReturn(mock())
//            whenever(pinMapper.freeStartedPin).thenReturn(mock())
//            whenever(pinMapper.freeFinishedPin).thenReturn(mock())
//        }

        describe("determinePin") {
            lateinit var adventure: Adventure

            context("purchasable") {

                context("completed") {
                    //beforeEachTest { adventure = adventureFactory(true, true, false) }

                    it("returns correct pin") {
                        //expect(pinMapper.determinePin(adventure)).equal(pinMapper.purchasableFinishedPin)
                    }
                }

                context("started") {

                }
            }

            context("free") {

                context("completed") {

                }

                context("started") {

                }

            }
        }
    }
})