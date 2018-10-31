package pl.reconizer.cityadventure.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository

class AuthenticationInterceptor(private val authenticationRepository: IAuthenticationRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authenticationRepository.getToken().blockingGet()
        val originalRequest = chain.request()
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        } else {
            return chain.proceed(originalRequest.newBuilder()
                    .header("authorization", "Bearer $token")
                    .build())
        }
    }

}