package pl.reconizer.cityadventure.data.network.interceptors

import com.winterbe.expekt.expect
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class AuthenticationInterceptorSpec : Spek({

    describe("AuthenticationInterceptor") {

        lateinit var mockServer: MockWebServer
        var token: String? = ""
        lateinit var request: RecordedRequest

        fun prepareRequest() {
            OkHttpClient().newBuilder().addInterceptor(AuthenticationInterceptor(token)).build()
                    .newCall(Request.Builder().url(mockServer.url("/")).build()).execute()
            request = mockServer.takeRequest()
        }

        beforeEachTest {
            mockServer = MockWebServer()
            mockServer.start()
            mockServer.enqueue(MockResponse())
        }
        afterEachTest { mockServer.shutdown() }

        context("when token is provided") {
            beforeEachTest {
                token = "token"
                prepareRequest()
            }

            it ("inserts authentication token into header") {
                expect(request.getHeader("authorization")).to.equal("Bearer $token")
            }
        }

        context("when token is not provided") {
            beforeEachTest {
                token = null
                prepareRequest()
            }

            it ("authentication header is empty") {
                expect(request.getHeader("authorization")).to.be.`null`
            }
        }


    }

})