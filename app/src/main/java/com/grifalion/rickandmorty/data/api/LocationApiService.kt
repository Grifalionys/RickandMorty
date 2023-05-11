package com.grifalion.rickandmorty.data.api

import com.grifalion.rickandmorty.data.api.repsonse.location.LocationResponse
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.location.Location
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApiService {
    @GET("location/")
    suspend fun getLocation(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): LocationResponse

    @GET("character/{id}")
    fun getListCharactersByIdIntoLocationDetail(@Path("id") id: String): Observable<List<CharacterResult>>

    @GET("location/")
    fun getDetailLocation(@Query("name") name: String): Observable<Location>

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
        var locationRetrofit: LocationApiService? = null
        fun getInstance(): LocationApiService {
            if(locationRetrofit == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
                val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                locationRetrofit = retrofit.create(LocationApiService::class.java)
            }
            return locationRetrofit!!
        }
    }
}