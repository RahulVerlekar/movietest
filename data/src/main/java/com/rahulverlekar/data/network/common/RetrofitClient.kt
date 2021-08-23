package com.rahulverlekar.data.network.common

import com.google.gson.GsonBuilder
import com.rahulverlekar.domain.KeyValueStorage
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class RetrofitClient @Inject constructor(private val keyValueStorage: KeyValueStorage) {
    private val builder : OkHttpClient.Builder by lazy {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor {
                val url = it.request().url().newBuilder().addQueryParameter("api_key", API_KEY_V3).build()
                val builder = it.request().newBuilder().url(url)

                this.keyValueStorage.token?.let { token ->
                    builder.addHeader("Authorization","Bearer $token")
                }

                it.proceed(builder.build())
            }

        builder
    }

    public fun build(baseUrl: String): Retrofit {
        val gsonBuilder = GsonBuilder()
        val converter = GsonConverterFactory.create(gsonBuilder.create())
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .addCallAdapterFactory(ErrorAdapterFactory())
            .client(builder.build())
            .build()
    }

    public fun build(): Retrofit {
        return build(BASE_URL)
    }

}
const val BASE_URL = "https://api.themoviedb.org/3/"

const val API_KEY= "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNWY4NmEwYTkwNzMwMDNlMDYwYzkxYWFjZjg1MjdlNCIsInN1YiI6IjVmYjNlY2I0MWYwMjc1MDAzZTVlNjk3YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0XnqevdCSx01XYXBR0jy-2PnUWuffXuJLa9zFYhwmC4"
const val API_KEY_V3 = "c5f86a0a9073003e060c91aacf8527e4"