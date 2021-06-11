package com.bofu.coroutinesretrofitmvvm.services

import com.bofu.coroutinesretrofitmvvm.models.Beer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MainApi {
    @GET("beers")
    fun getBeers() : Call<ArrayList<Beer>>
}

class MainService {

    private val api: MainApi

    init {
        val retrofit = Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(
            GsonConverterFactory.create()).build()
        api = retrofit.create(MainApi::class.java)
    }


    fun getBeers(callback: Callback<ArrayList<Beer>>){
        val call = api.getBeers()
        call.enqueue(callback)
    }

    companion object {
        private const val BaseUrl = "https://api.punkapi.com/v2/"
    }
}