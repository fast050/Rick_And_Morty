package com.example.rickandmorty.data.network

import com.example.rickandmorty.data.model.CharacterResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacter(@Query("page") page:String) :Response<CharacterResponse>

}

object RickAndMortyApi{

    private const val BaseUrl = "https://rickandmortyapi.com/api/"

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)


    private val okHttp = OkHttpClient.Builder()
                        .addInterceptor(logger)
        .callTimeout(5,TimeUnit.SECONDS)
        .build()

    private val retrofit= Retrofit.Builder().baseUrl(BaseUrl)
                           .addConverterFactory(GsonConverterFactory.create())
                           .client(okHttp).build()

    val retrofitInstance :RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)
    }

}


