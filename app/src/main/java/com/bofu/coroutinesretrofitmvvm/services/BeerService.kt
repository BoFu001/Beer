package com.bofu.coroutinesretrofitmvvm.services

import com.bofu.coroutinesretrofitmvvm.models.Beer
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface BeerApi {
    @GET("beers")
    suspend fun getBeers() : ArrayList<Beer>
}

class BeerService {

    private val beerApi: BeerApi by lazy {
        val retrofit = Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        retrofit.create(BeerApi::class.java)
    }



    suspend fun getBeers(): Flow<Beer> {
        return flow {
            val countries = beerApi.getBeers()
            countries.forEach {
                emit(it)
            }
        }
    }



    companion object {
        private const val BaseUrl = "https://api.punkapi.com/v2/"
    }
}