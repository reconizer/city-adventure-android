package pl.reconizer.cityadventure.data.repositories

import com.nhaarman.mockitokotlin2.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.local.ILocalStorage

class AuthenticationRepositorySpec : Spek({

    describe("AuthenticationRepository") {
        val localStorage: ILocalStorage = mock()
        val repository = AuthenticationRepository(localStorage)
        val token = "token"

        afterEachTest {
            reset(localStorage)
        }

        describe("getToken") {

            context("when token is saved") {

                before {
                    whenever(localStorage[any()]).thenReturn(token)
                }

                it("emits stored token") {
                    val testObserver = repository.getToken().test()
                    testObserver.assertValue(token)
                    testObserver.assertComplete()
                }
            }

            context("when token is not saved") {

                before {
                    whenever(localStorage[any()]).thenReturn(null)
                }

                it("emits empty value") {
                    val testObserver = repository.getToken().test()
                    testObserver.assertValue("")
                    testObserver.assertComplete()
                }
            }

        }

        describe("saveToken") {

            it("saves the value") {
                val testObserver = repository.saveToken(token).test()
                testObserver.assertComplete()
                verify(localStorage, atLeastOnce()).set(any(), eq(token))
            }

        }

        describe("clearToken") {

            it("clears saved token") {
                val testObserver = repository.clearToken().test()
                testObserver.assertComplete()
                verify(localStorage, atLeastOnce()).remove(any())
            }
        }

    }

})