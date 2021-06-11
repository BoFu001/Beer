package com.bofu.coroutinesretrofitmvvm.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.bofu.coroutinesretrofitmvvm.models.Beer
import com.bofu.coroutinesretrofitmvvm.models.YearFilter
import com.bofu.coroutinesretrofitmvvm.services.BeerService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class MainViewModel: ViewModel() {

    private val TAG = javaClass.simpleName
    private val beerService = BeerService()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, ": Exception: ${throwable.localizedMessage}")
    }
    private val data = ArrayList<Beer>()
    val isLoading = MutableLiveData(false)
    val liveData = MutableLiveData<ArrayList<Beer>>()
    var yearFilter: YearFilter? = null

    init {
        fetchData()
    }

    private fun fetchData() {
        println("zzz 3")

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            beerService.getBeers()
                .onStart {
                    isLoading.value = true
                }
                .onCompletion {
                    isLoading.value = false
                }.collect {
                    data.add(it)
                    liveData.value = data
                }
        }
    }

    fun filterData(filter: YearFilter?){
        filter?.let {

            yearFilter = filter

            liveData.value = data.filter {
                it.year >= filter.start && it.year <= filter.end
            } as ArrayList<Beer>
        }
    }

}


