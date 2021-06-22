package com.bofu.coroutinesretrofitmvvm.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bofu.coroutinesretrofitmvvm.extensions.isConnectedToNetwork
import com.bofu.coroutinesretrofitmvvm.models.Beer
import com.bofu.coroutinesretrofitmvvm.models.YearFilter
import com.bofu.coroutinesretrofitmvvm.services.BeerService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel(application: Application) : AndroidViewModel (application) {

    private val TAG = javaClass.simpleName
    private val beerService = BeerService()
    private val data = ArrayList<Beer>()
    val isLoading = MutableLiveData(false)
    val liveData = MutableLiveData<ArrayList<Beer>>()
    var yearFilter: YearFilter? = null
    val hasConnection = MutableLiveData(false)

    init {
        fetchData()
    }

    private fun checkConnection(): Boolean {
        val isConnected = getApplication<Application>().applicationContext.isConnectedToNetwork()
        when(isConnected){
            true -> hasConnection.value = true
            false -> hasConnection.value = false
        }
        return isConnected
    }

    fun fetchData() {

        if(!checkConnection()) return

        Log.d(TAG, ": beginning fetchData")

        viewModelScope.launch {
            beerService.getBeers()
                .onStart {
                    isLoading.value = true
                }
                .catch { exception ->
                    Log.d(TAG, ": Exception: ${exception.localizedMessage}")
                    hasConnection.value = false
                }
                .onCompletion {
                    isLoading.value = false
                    filterData()
                }.collect {
                    data.add(it)
                }
        }
    }

    fun setFilter(filter: YearFilter?){
        filter?.let {
            yearFilter = filter
            filterData()
        }
    }

    private fun filterData(){
        if(yearFilter == null){
            liveData.value = data
        } else {
            liveData.value = data.filter { beer ->
                beer.year >= yearFilter!!.start && beer.year <= yearFilter!!.end
            } as ArrayList<Beer>
        }
    }



}


