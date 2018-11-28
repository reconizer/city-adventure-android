package pl.reconizer.cityadventure.presentation.map

import com.nhaarman.mockitokotlin2.*
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
                started = started,
                purchased = false
        )
    }

    describe("AdventurePinMapper") {
        val pinProvider = mock<PinProvider>()
        var pinMapper = AdventurePinMapper(pinProvider)

        beforeEachTest { reset(pinProvider) }

        describe("determinePin") {
            lateinit var adventure: Adventure

            context("purchasable") {

                context("not started") {
                    beforeEachTest { adventure = adventureFactory(true, false, false) }

                    it("returns correct pin") {
                        pinMapper.determinePin(adventure)
                        verify(pinProvider, atLeastOnce()).purchasablePin
                    }
                }

                context("completed") {
                    beforeEachTest { adventure = adventureFactory(true, true, false) }

                    it("returns correct pin") {
                        pinMapper.determinePin(adventure)
                        verify(pinProvider, atLeastOnce()).purchasableFinishedPin
                    }
                }

                context("started") {
                    beforeEachTest { adventure = adventureFactory(true, false, true) }

                    it("returns correct pin") {
                        pinMapper.determinePin(adventure)
                        verify(pinProvider, atLeastOnce()).purchasableStartedPin
                    }
                }
            }

            context("free") {

                context("not started") {
                    beforeEachTest { adventure = adventureFactory(false, false, false) }

                    it("returns correct pin") {
                        pinMapper.determinePin(adventure)
                        verify(pinProvider, atLeastOnce()).freePin
                    }
                }

                context("completed") {
                    beforeEachTest { adventure = adventureFactory(false, true, false) }

                    it("returns correct pin") {
                        pinMapper.determinePin(adventure)
                        verify(pinProvider, atLeastOnce()).freeFinishedPin
                    }
                }

                context("started") {
                    beforeEachTest { adventure = adventureFactory(false, false, true) }

                    it("returns correct pin") {
                        pinMapper.determinePin(adventure)
                        verify(pinProvider, atLeastOnce()).freeStartedPin
                    }
                }

            }

        }

    }

})