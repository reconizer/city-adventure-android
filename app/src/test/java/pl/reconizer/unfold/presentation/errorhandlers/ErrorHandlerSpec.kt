package pl.reconizer.unfold.presentation.errorhandlers

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.nhaarman.mockitokotlin2.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.presentation.mvp.IView
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.ref.WeakReference

class ErrorHandlerSpec : Spek({

    describe("ErrorHandler") {
        val gson = mock<Gson>()
        val view = mock<IView>()

        lateinit var errorsHandler: ErrorsHandler<Error>

        beforeEachTest {
            errorsHandler = spy(ErrorsHandler.build(gson))
            errorsHandler.view = WeakReference(view)
        }

        afterEachTest {
            reset(view)
            reset(gson)
        }

        describe("onError") {
            lateinit var exception: Throwable

            context("when HttpException") {

                fun prepareException(status: Int): HttpException {
                    val response = Response.error<Error>(status, ResponseBody.create(MediaType.parse("application/json"), ""))
                    return HttpException(response)
                }

                context("when status 422") {
                    before {
                        exception = prepareException(422)
                    }

                    it("handles status") {
                        whenever(gson.fromJson(any<String>(), any<Class<Error>>())).thenReturn(Error(null))
                        errorsHandler.onError(exception)
                        verify(errorsHandler, atLeastOnce()).handleUnprocessableEntityError(any())
                    }
                }

                context("when status 401") {
                    before {
                        exception = prepareException(401)
                    }

                    it("handles status") {
                        errorsHandler.onError(exception)
                        verify(errorsHandler, atLeastOnce()).handleUnauthorizedError()
                    }
                }

                context("when status 500") {
                    before {
                        exception = prepareException(500)
                    }

                    it("handles status") {
                        errorsHandler.onError(exception)
                        verify(errorsHandler, atLeastOnce()).handleServerError()
                    }
                }

            }

            context("when IOException") {
                val exception = IOException()

                it("handles error") {
                    errorsHandler.onError(exception)
                    verify(errorsHandler, atLeastOnce()).handleNetworkError()
                }
            }

            context("when exception") {
                val exception = Exception()

                it("handles error") {
                    errorsHandler.onError(exception)
                    verify(errorsHandler, atLeastOnce()).handleUnknownError(exception)
                }
            }

        }

        describe("handleUnknownError") {
            val exception = Throwable()

            it("calls showGenericError in view") {
                errorsHandler.handleUnknownError(exception)
                verify(view, atLeastOnce()).showGenericError()
            }
        }

        describe("handleNetworkError") {

            it("calls showConnectionError in view") {
                errorsHandler.handleNetworkError()
                verify(view, atLeastOnce()).showConnectionError()
            }

        }

        describe("handleUnauthorizedError") {

            it("calls showAuthorizationError in view") {
                errorsHandler.handleUnauthorizedError()
                verify(view, atLeastOnce()).showAuthorizationError()
            }

        }

        describe("handleUnprocessableEntityError") {
            val errorEntity = Error(listOf("some_code"))
            val errorJson = "{\"base\": [\"some_code\"]}"
            lateinit var exception: HttpException

            beforeEachTest {
                val response = Response.error<Error>(422, ResponseBody.create(MediaType.parse("application/json"), errorJson))
                exception = HttpException(response)
            }

            context("valid error format") {

                it("calls showParametrizedError in view with correct entity") {
                    whenever(gson.fromJson(errorJson, Error::class.java)).thenReturn(errorEntity)
                    errorsHandler.handleUnprocessableEntityError(exception)
                    verify(view, atLeastOnce()).showParametrizedError(errorEntity)
                }
            }

            context("invalid error format") {
                it("calls showParametrizedError in view with correct entity") {
                    val emptyError = Error(null)
                    whenever(gson.fromJson(errorJson, Error::class.java)).thenThrow(JsonSyntaxException::class.java)
                    whenever(gson.fromJson("{}", Error::class.java)).thenReturn(emptyError)
                    errorsHandler.handleUnprocessableEntityError(exception)
                    verify(view, atLeastOnce()).showParametrizedError(emptyError)
                }
            }
        }

        describe("handleServerError") {

            it("calls showServerError in view") {
                errorsHandler.handleServerError()
                verify(view, atLeastOnce()).showServerError()
            }

        }

    }

})