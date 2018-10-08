package pl.reconizer.cityadventure.data.network.interceptors

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val token: String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
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